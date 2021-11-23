package com.example.denizgencayspacexfan.data.models


data class UserModel(
    val email: String,
    val userId: String,
)

data class UserCollectionModel(
    val rocketIds: ArrayList<String>
)