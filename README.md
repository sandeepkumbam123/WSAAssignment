# WSAAssignment

This application is written in Kotlin with MVVM + Clean Architecture and Jetpack Compose UI

Main Implementation of this application includes 
1. Showing list of Trending Movies in this Week
2. Search Functionality to display list of Movies with given Key word
3. Show Details Screen on click of each Movie Item
4. Details Screen includes the details about the Movie ,Rating,voting and an action to Favorite this Movie.
5. There is a provision to show list of similar show based on the detail screen movie Item
6. Implemented Database to store the favorites item to store locally and show the Favorite Icon on Trending/Search Movies List if any exists.



============================================================
Trending Movies on Launch of Application 
![WSAAssignment3](https://github.com/sandeepkumbam123/WSAAssignment/assets/18414888/9c57fcea-7333-427a-8e12-2e85bdad6e5c)

Search List of Movies 
![WSAAssignment2](https://github.com/sandeepkumbam123/WSAAssignment/assets/18414888/0129e50e-4624-4cc9-9dc2-7fd71944761c)

Both images also includes the Favorite Icon Highlighted if the user has already favorited it.

Details Screen
![WSAAssignment1](https://github.com/sandeepkumbam123/WSAAssignment/assets/18414888/dec8212a-cb89-4c16-a6db-ae37a6decd31)


Database - Used RoomDatabase with DAO Architecture to implement Local Database
Dependency Injection - Hilt 
Architecture - MVVM + Clean 
Pagination - PagingSource 3.0
Network - Retrofit


List of classes Flow used to interect or render UI 

UI (Activity/ComponetActivity)  <--- ViewModel (StateFlow) <--> UseCase <--> Repository <---> Retrofit
                                                                               |
                                                                               |
                                                                               |
                                                                               V
                                                                           if(Pagination) <-> PagingDatasource    <--> Retrofit    



