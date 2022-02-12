package top.stores.companyappassement.models

import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull

data class CompanyListPojo(@NotNull
                           @SerializedName("id")
                           var companyID: Int = 0,

                           @SerializedName("username")
                           var userName: String? = "",

                           @SerializedName("company")
                           var companyDetails : List<Company>)
