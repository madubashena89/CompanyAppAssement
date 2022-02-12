package top.stores.companyappassement.models

import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull

data class Company(@NotNull
                   @SerializedName("name")
                   var companyName: String? = "")
