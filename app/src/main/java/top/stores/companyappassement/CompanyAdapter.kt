package top.stores.companyappassement

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import top.stores.companyappassement.models.CompanyDetailsPojo
import top.stores.companyappassement.models.CompanyListPojo

class CompanyAdapter (private val context: Context?, private val companyList: List<CompanyListPojo>?,
                      private val companyDetailsList: List<CompanyDetailsPojo>?)
    : RecyclerView.Adapter<CompanyAdapter.SportsAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SportsAdapterViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.company_card, parent, false)
        return SportsAdapterViewHolder(v)
    }

    override fun getItemCount(): Int {
        return companyList?.size!!

    }

    override fun onBindViewHolder(holder: SportsAdapterViewHolder, position: Int) {
        holder.tvCompanyID.text = companyList?.get(position)?.companyID.toString()
        holder.tvCompanyTitle.text = companyDetailsList?.get(position)?.title
        holder.tvCompanyBody.text = companyDetailsList?.get(position)?.companyBody
    }


    class SportsAdapterViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
        var tvCompanyID : TextView
        var tvCompanyTitle: TextView
        var tvCompanyBody : TextView

        init {
            tvCompanyID = itemView.findViewById(R.id.tv_company_ID)
            tvCompanyTitle = itemView.findViewById(R.id.tv_company_title)
            tvCompanyBody = itemView.findViewById((R.id.tv_company_body))
        }

    }

}