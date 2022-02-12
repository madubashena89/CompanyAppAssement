package top.stores.companyappassement

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.json.JSONException
import org.json.JSONObject
import top.stores.companyappassement.models.CompanyDetailsList
import top.stores.companyappassement.models.CompanyDetailsPojo
import top.stores.companyappassement.models.CompanyList
import top.stores.companyappassement.models.CompanyListPojo
import top.stores.companyappassement.networkCalls.VolleySingleton
import java.lang.reflect.Type
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var companyAdapter : CompanyAdapter
    private lateinit var recyclerView : RecyclerView
    private var companyList: CompanyList = CompanyList()
    private var companyDetailList: CompanyDetailsList = CompanyDetailsList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.companyRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchCompanyData(this.application)
        fetchCompanyDetailsData(this.application)

    }

    fun fetchCompanyData(application: Application) {
        val COMPANY_URL = "https://jsonplaceholder.typicode.com/users"
        val request = JsonArrayRequest(
            Request.Method.GET, // method
            COMPANY_URL, // url
            null, {
                val response = it
                Log.d("Response ", "$it")
                try {
                    val payLoad = it
                    val gson = GsonBuilder().create()
                    val groupListType: Type =
                        object : TypeToken<ArrayList<CompanyListPojo>>() {}.type
                    companyList.companies = gson.fromJson(payLoad.toString(), groupListType)

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {
                Log.d("Response", "$it")
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
                Log.d("RESPONSERwee", "fetchCompanyDetailsData: $it")
                try {
                    val payLoad = it
                    val gson = GsonBuilder().create()
                    val groupListType: Type =
                        object : TypeToken<ArrayList<CompanyDetailsPojo>>() {}.type
                    companyDetailList.company = gson.fromJson(payLoad.toString(), groupListType)
                    companyList.companies?.forEach {first ->
                        companyDetailList.company?.forEach { second ->
                            if (first.companyID.equals(second.companyDetailsID)){
                                companyAdapter = CompanyAdapter( this?.applicationContext,  companyList.companies,  companyDetailList.company)
                                val llm = LinearLayoutManager(this)
                                llm.orientation = LinearLayoutManager.VERTICAL
                                recyclerView.setLayoutManager(llm)
                                recyclerView.adapter = companyAdapter
                            }
                        }
                    }


                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {
                Log.d("ErrorResponse", "fetchCompanyDetailsData: $it")
                Toast.makeText(
                    application.applicationContext,
                    "Sorry Something went wrong from our end when loading the Companies!!",
                    Toast.LENGTH_LONG
                ).show()

            })
        VolleySingleton.getInstance(application.applicationContext).addToRequestQueue(request)
    }
}