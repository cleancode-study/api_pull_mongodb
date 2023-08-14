package com.samsung.api_pull_mongodb.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service
import org.bson.Document
import org.json.JSONArray

@Service
class MongoService(@Autowired val mongoTemplate: MongoTemplate) {

    fun saveJsonData(jsonData: String) {
//        println("test")
//        println(jsonData)
        val collection = mongoTemplate.getCollection("save_api")

        // Parse the jsonData as a JSON array
        val jsonArray = JSONArray(jsonData)
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val document = Document.parse(jsonObject.toString())
            collection.insertOne(document)
        }
    }
}