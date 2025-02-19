package com.example.hanyarunrun.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.hanyarunrun.data.AppDatabase
import com.example.hanyarunrun.data.DataEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DataViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).dataDao()

    // LiveData utama untuk menyimpan data dari database
    private val _dataList = MutableLiveData<List<DataEntity>>()
    val dataList: LiveData<List<DataEntity>> get() = _dataList

    // LiveData untuk pencarian & filter
    private val _filteredDataList = MutableLiveData<List<DataEntity>>()
    val filteredDataList: LiveData<List<DataEntity>> get() = _filteredDataList

    init {
        loadAllData()
    }

    // Memuat ulang semua data dari database
    fun loadAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            val allData = dao.getAllSync() // Gunakan metode DAO yang mengembalikan List langsung
            _dataList.postValue(allData)
            _filteredDataList.postValue(allData) // Default: tampilkan semua data
        }
    }

    // Fungsi pencarian berdasarkan nama provinsi atau kabupaten/kota
    fun searchData(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val allData = _dataList.value ?: emptyList() // Ambil data terbaru
            val filtered = if (query.isEmpty()) allData else allData.filter {
                it.namaProvinsi.contains(query, ignoreCase = true) ||
                        it.namaKabupatenKota.contains(query, ignoreCase = true)
            }
            _filteredDataList.postValue(filtered)
        }
    }

    // Fungsi filter berdasarkan tahun
    fun filterByYear(year: Int?) {
        viewModelScope.launch(Dispatchers.IO) {
            val allData = _dataList.value ?: emptyList()
            val filtered = if (year == null) allData else allData.filter { it.tahun == year }
            _filteredDataList.postValue(filtered)
        }
    }

    fun insertData(
        kodeProvinsi: String,
        namaProvinsi: String,
        kodeKabupatenKota: String,
        namaKabupatenKota: String,
        total: String,
        satuan: String,
        tahun: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val totalValue = total.toDoubleOrNull() ?: 0.0
            val tahunValue = tahun.toIntOrNull() ?: 0
            dao.insert(
                DataEntity(
                    kodeProvinsi = kodeProvinsi,
                    namaProvinsi = namaProvinsi,
                    kodeKabupatenKota = kodeKabupatenKota,
                    namaKabupatenKota = namaKabupatenKota,
                    total = totalValue,
                    satuan = satuan,
                    tahun = tahunValue
                )
            )
            loadAllData() // Memuat ulang data setelah insert
        }
    }

    fun updateData(data: DataEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.update(data)
            loadAllData() // Memuat ulang data setelah update
        }
    }

    suspend fun getDataById(id: Int): DataEntity? {
        return withContext(Dispatchers.IO) {
            dao.getById(id)
        }
    }

    fun deleteDataById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = getDataById(id)
            data?.let {
                dao.delete(it)
                loadAllData() // Memuat ulang data setelah delete
            }
        }
    }
}
