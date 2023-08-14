package com.samsung.api_pull_mongodb.controller

import com.samsung.api_pull_mongodb.service.MongoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
@RestController
class ApiController(@Autowired val mongoService: MongoService) {

    private val apiUrl = "https://jsonplaceholder.typicode.com/comments"
    private val apiKey = "YOUR_API_KEY"

    @GetMapping("/fetch-and-store-comments")
    fun fetchAndStoreComments(): ResponseEntity<String> {
        val restTemplate = RestTemplate()
        val response = restTemplate.getForEntity(apiUrl, String::class.java)

        if (response.statusCode.is2xxSuccessful) {
            mongoService.saveJsonData(response.body!!)
            return ResponseEntity.ok("Comments saved successfully!")
        }

        return ResponseEntity.status(response.statusCode).body("Failed to fetch comments!")
    }

    @PostMapping("/fetch-and-store")
    fun fetchAndStoreData(): ResponseEntity<String> {
        val headers = HttpHeaders()
//        headers.set("Authorization", "Bearer $apiKey")
        headers.set("Accept", "application/json")

        val entity = ResponseEntity<String>(headers, HttpStatus.OK)

        val restTemplate = RestTemplate()
        val response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String::class.java)

        if (response.statusCode.is2xxSuccessful) {
            mongoService.saveJsonData(response.body!!)
            return ResponseEntity.ok("Data saved successfully!")
        }

        return ResponseEntity.status(response.statusCode).body("Failed to fetch data!")
    }
}