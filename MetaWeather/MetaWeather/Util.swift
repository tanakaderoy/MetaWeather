//
//  Util.swift
//  MetaWeather
//
//  Created by Tanaka Mazivanhanga on 7/31/19.
//  Copyright © 2019 Tanaka Mazivanhanga. All rights reserved.
//

import Foundation

extension Array {
    func isValidIndex(_ index: Int) -> Bool {
        return index >= 0 && index < self.count
    }
}

extension String {
    func trim() -> String
    {
        return self.trimmingCharacters(in: CharacterSet.whitespaces)
    }
}

extension Double {
    func toFahrenheit() -> Double {
        return self * 9 / 5 + 32
    }
    
}
