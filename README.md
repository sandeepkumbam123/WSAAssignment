WSAAssignment
This application is written in Kotlin with MVVM + Clean Architecture and Jetpack Compose UI

Main Implementation of this application includes

Showing list of Trending Movies in this Week
Search Functionality to display list of Movies with given Key word
Show Details Screen on click of each Movie Item
Details Screen includes the details about the Movie ,Rating,voting and an action to Favorite this Movie.
There is a provision to show list of similar show based on the detail screen movie Item
Implemented Database to store the favorites item to store locally and show the Favorite Icon on Trending/Search Movies List if any exists.
============================================================ Trending Movies on Launch of Application WSAAssignment3
![WSAAssignment3](https://github.com/sandeepkumbam123/WSAAssignment/assets/18414888/39a7df5d-322b-49e5-a252-67b427e1d328)

Search List of Movies WSAAssignment2
![WSAAssignment2](https://github.com/sandeepkumbam123/WSAAssignment/assets/18414888/b706bcb5-e803-429d-99dc-6f8767ff6706)

Both images also includes the Favorite Icon Highlighted if the user has already favorited it.
![WSAAssignment1](https://github.com/sandeepkumbam123/WSAAssignment/assets/18414888/475b0182-30da-4285-9551-b20a552299e3)

Details Screen WSAAssignment1

Database - Used RoomDatabase with DAO Architecture to implement Local Database Dependency Injection - Hilt Architecture - MVVM + Clean Pagination - PagingSource 3.0 Network - Retrofit

List of classes Flow used to interect or render UI

UI (Activity/ComponetActivity) <--- ViewModel (StateFlow) <--> UseCase <--> Repository <---> Retrofit | | | V if(Pagination) <-> PagingDatasource <--> Retrofit
