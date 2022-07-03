# TRVL
This Application was developed as an assignment for  an Android Development Course at International Hellenic University

Trvl is a simple Travel Agency app that allows the user to :

* Add/Update/Delete Travel Agency office.
* Add/Update/Delete a specific tour destination.
* Add/Update/Delete hotels tied to a specific tour destination.
* Add/Update/Delete vacation packages.
* Add/Update/Delete a vacation reservation
* You can locate your destination using google maps
* Notifications for every action
* Every action is using gestures


## Screenshots

[<img src="/demo_images/menu.png" align="left"
width="200"
    hspace="10" vspace="10">](/demo_images/menu.png)

[<img src="/demo_images/agencies.png" align="left"
width="200"
    hspace="10" vspace="10">](/demo_images/agencies.png)

[<img src="/demo_images/packages.png" align="left"
width="200"
    hspace="10" vspace="10">](/demo_images/packages.png)

[<img src="/demo_images/add_package.png"  align="left"
width="200"
    hspace="10" vspace="10">](/demo_images/add_package.png)

[<img src="/demo_images/add_package2.png" align="left"
width="200"
    hspace="10" vspace="10">](/demo_images/add_package2.png)

[<img src="/demo_images/help.png"
width="200"
    hspace="10" vspace="10">](/demo_images/help.png)
    
## Implementation

Project is implemented following Clean MVVM architectural pattern and uses:

* Room Api:[https://developer.android.com/training/data-storage/room](https://developer.android.com/training/data-storage/room)
* Navigation: [https://developer.android.com/guide/navigation](https://developer.android.com/guide/navigation)
* LiveData: [https://developer.android.com/topic/libraries/architecture/livedata](https://developer.android.com/topic/libraries/architecture/livedata)
* ViewModel: [https://developer.android.com/topic/libraries/architecture/viewmodel](https://developer.android.com/topic/libraries/architecture/viewmodel)
* ViewBinding: [https://developer.android.com/topic/libraries/view-binding](https://developer.android.com/topic/libraries/view-binding)


## Installation

You have to make Create and Add to your /local.properties file your personal  Google Maps API_KEY.
```
MAPS_API_KEY=yourKEY
```
