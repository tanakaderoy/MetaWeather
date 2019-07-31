//
//  WeatherModel.swift
//  MetaWeather
//
//  Created by Tanaka Mazivanhanga on 7/31/19.
//  Copyright © 2019 Tanaka Mazivanhanga. All rights reserved.
//

import Foundation
class WeatherRoot: Codable {
    
    let title: String
    let location_type: String
    let woeid: Int
    let latt_long: String
    let consolidatedWeather: [ConsolidatedWeather]
    
    
    enum CodingKeys: String, CodingKey {
        case title = "title"
        case location_type = "location_type"
        case woeid = "woeid"
        case latt_long = "latt_long"
        case consolidatedWeather = "consolidated_weather"
    }
    
}

class ConsolidatedWeather: Codable {
    let id: Int
    let weather_state_name: String
    let weather_state_abbr: String
    let wind_direction_compass: String
    let applicable_date: String
    let min_temp: Double
    let max_temp: Double
    let the_temp: Double
    let wind_speed: Double
    let humidity: Int
    let predictability: Int
    
    enum CodingKeys: String, CodingKey {
        case id = "id"
        case weather_state_name = "weather_state_name"
        case weather_state_abbr = "weather_state_abbr"
        case wind_direction_compass = "wind_direction_compass"
        case applicable_date = "applicable_date"
        case min_temp = "min_temp"
        case max_temp = "max_temp"
        case the_temp = "the_temp"
        case wind_speed = "wind_speed"
        case humidity = "humidity"
        case predictability = "predictability"
        
    }
    
    
    
}


