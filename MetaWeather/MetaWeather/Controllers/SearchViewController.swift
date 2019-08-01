//
//  SearchViewController.swift
//  MetaWeather
//
//  Created by Tanaka Mazivanhanga on 8/1/19.
//  Copyright © 2019 Tanaka Mazivanhanga. All rights reserved.
//

import UIKit
import CoreData


protocol SearchCityDelegate {
    func userEnteredANewCityName(city: String)
}


class SearchViewController: UIViewController {
    
    var delegate: SearchCityDelegate?
    
    @IBOutlet weak var searchTextField: UITextField!
    var results = [SearchResult]()
    @IBOutlet weak var tableView: UITableView!
    let context = (UIApplication.shared.delegate as! AppDelegate).persistentContainer.viewContext
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
        // Do any additional setup after loading the view.
    }
    override func viewWillAppear(_ animated: Bool) {
        loadSearchResults()
    }
    
    func loadSearchResults() {
        //specify data type
        let request: NSFetchRequest<SearchResult> = SearchResult.fetchRequest()
        tableView.reloadData()
        fetchRequest(request: request)
    }
    
    @IBAction func goButtonPressed(_ sender: UIButton) {
        let text = searchTextField.text
        if text != "" {
            let newResult = SearchResult(context: self.context)
            newResult.title = text
            newResult.woeid = 21233
            self.results.append(newResult)
            self.saveSearchResults()
            delegate?.userEnteredANewCityName(city: text!)
            self.navigationController?.popViewController(animated: true)
        }else{
            searchTextField.endEditing(true)
        }
    }
    
    
    func saveSearchResults(){
        
        do{
            try context.save()
            
        }catch{
            print("error saving context \(error)")
        }
        self.tableView.reloadData()
        
    }
    func fetchRequest(request: NSFetchRequest<SearchResult>){
        do {
            results = try context.fetch(request)
        } catch  {
            print("error \(error)")
        }
        
    }
    
    
    
    /*
     // MARK: - Navigation
     
     // In a storyboard-based application, you will often want to do a little preparation before navigation
     override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
     // Get the new view controller using segue.destination.
     // Pass the selected object to the new view controller.
     }
     */
    
}
extension SearchViewController: UITableViewDelegate, UITableViewDataSource{
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return results.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "searchResultCell", for: indexPath)
        cell.textLabel?.text = results[indexPath.row].title
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        guard let city = results[indexPath.row].title else { return  }
        delegate?.userEnteredANewCityName(city: city)
        self.navigationController?.popViewController(animated: true)
        
    }
    
    
}
