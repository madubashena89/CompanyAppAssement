package top.stores.companyappassement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import top.stores.companyappassement.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel : MainActivityViewModel
    private lateinit var sportsAdapter : CompanyAdapter
    private lateinit var recyclerView : RecyclerView
    private var companyTempID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get<MainActivityViewModel>(MainActivityViewModel::class.java)
        recyclerView = findViewById(R.id.companyRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.getSCompanies(this.application)?.observe(this, Observer {firstList ->
            firstList.forEach {
                it.companyID = companyTempID
                viewModel.getSCompaniesDetails(this.application)?.observe(this, Observer {secondList ->
                    secondList.forEach {
                        if (it.companyDetailsID == companyTempID)
                            sportsAdapter = CompanyAdapter( this?.applicationContext, firstList,secondList )
                        val llm = LinearLayoutManager(this.applicationContext)
                        llm.orientation = LinearLayoutManager.VERTICAL
                        recyclerView.setLayoutManager(llm)
                        recyclerView.adapter=sportsAdapter
                    }
                })
            }
        })


    }
}