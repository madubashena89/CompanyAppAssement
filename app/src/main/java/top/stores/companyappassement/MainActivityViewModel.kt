package top.stores.companyappassement

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Job
import org.json.JSONException
import top.stores.companyappassement.models.CompanyDetailsList
import top.stores.companyappassement.models.CompanyDetailsPojo
import top.stores.companyappassement.models.CompanyList
import top.stores.companyappassement.models.CompanyListPojo
import top.stores.companyappassement.networkCalls.VolleySingleton
import java.lang.reflect.Type
import java.util.ArrayList

class MainActivityViewModel : ViewModel() {

    var job: Job? = null
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()
    private var companyListLiveData: MutableLiveData<List<CompanyListPojo>>? = null
    private var companyDetailsListLiveData: MutableLiveData<List<CompanyDetailsPojo>>? = null

    fun getSCompanies(application: Application): MutableLiveData<List<CompanyListPojo>>? {
        if (companyListLiveData == null) {
            companyListLiveData = MutableLiveData<List<CompanyListPojo>>()
            fetchCompanyData(application)
        }
        return companyListLiveData
    }

    fun getSCompaniesDetails(application: Application): MutableLiveData<List<CompanyDetailsPojo>>? {
        if (companyDetailsListLiveData == null) {
            companyDetailsListLiveData = MutableLiveData<List<CompanyDetailsPojo>>()
            fetchCompanyDetailsData(application)
        }
        return companyDetailsListLiveData
    }


    fun fetchCompanyData(application: Application) {
        val COMPANY_URL = "https://jsonplaceholder.typicode.com/users"
        val request = JsonArrayRequest(
            Request.Method.GET, // method
            COMPANY_URL, // url
            null, {
                val response = it
                try {
                    val payLoad = it
                    val gson = GsonBuilder().create()
                    val groupListType: Type =
                        object : TypeToken<ArrayList<CompanyListPojo>>() {}.type
                    var companyList: CompanyList = CompanyList()
                    companyList.companies = gson.fromJson(payLoad.toString(), groupListType)
                    companyListLiveData?.value = companyList.companies

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {
                Toast.makeText(
                    application.applicationContext,
                    "Sorry Something went wrong from our end when loading the Companies!!",
                    Toast.LENGTH_LONG
                ).show()

            })
        VolleySingleton.getInstance(application.applicationContext).addToRequestQueue(request)
    }


    fun fetchCompanyDetailsData(application: Application) {
        val COMPANY_DETAIL_URL = "https://jsonplaceholder.typicode.com/posts"
        val request = JsonArrayRequest(
            Request.Method.POST, // method
            COMPANY_DETAIL_URL, // url
            null, {
                val response = it
                try {
                    val payLoad = it
                    val gson = GsonBuilder().create()
                    val groupListType: Type =
                        object : TypeToken<ArrayList<CompanyDetailsPojo>>() {}.type
                    var companyList: CompanyDetailsList = CompanyDetailsList()
                    companyList.company = gson.fromJson(payLoad.toString(), groupListType)
                    companyDetailsListLiveData?.value = companyList.company

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {
                Toast.makeText(
                    application.applicationContext,
                    "Sorry Something went wrong from our end when loading the Companies!!",
                    Toast.LENGTH_LONG
                ).show()

            })
        VolleySingleton.getInstance(application.applicationContext).addToRequestQueue(request)
    }

//    fun getCompanyByID(id: Int): LiveData<ProjectEntity>? {
//        return repository.getProject(id)
//    }


    private fun onError(message: String) {
        error.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}
