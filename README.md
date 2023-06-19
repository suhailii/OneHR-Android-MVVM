# OneHR-Android-MVVM
![telegram-cloud-document-5-6276098571339041266](https://github.com/suhailii/OneHR-Android-MVVM/assets/74708728/a4f280bb-751a-41d0-bef4-b9e809898429)


## Project Description
The OneHR Android App is a human resources management application designed to streamline various HR processes. It provides a user-friendly interface for tracking attendance, and handling leave and claim requests. The app incorporates the MVVM (Model-View-ViewModel) architectural pattern for better code organization, separation of concerns, and maintainability.

# Key Features:

**1. Attendance using GPS verification**
- Checks in/out user depending on location retrieve from GPS
- Sucess when location matches office longitude and latitude.
- Toast message when invalid location retrieved.

**2. Leave application**
- Apply leave by selecting leave type through dropdown list (Spinner)
- Select dates through a DatePicker
- Able to edit/delete leave applied

**3. Claim application**
- Enter amount needed to claim
- Upload images of proof through Camera or Gallery

**4. Manager Approval/Rejection**
- Manager able to approve/reject leaves
- Manager able to approve/reject claims

**5. Notifications**
- Receive notifications whenever leave approved/rejected
- Receive notifications whenever claim approved/rejected
- Receive notification whenever room ID of Video Conference is notified.

**6. Video Conferencing through JitsiMeet**
- Video conferencing with controls such as mute, disable camera etc.
- Managers will be able to create conference room with room ID specified
- Managers able to notify the room ID through notifications
- Employees will be able to join with the room ID specified.

**7. Database storage**
- Firebase Realtime Database for storing data for user, leaves and claims.
- Firebase Storage used for storing of images uploaded by employees.
- RoomDB(SQlite) for storing notifications of users locally

## Screenshots
<img src="https://github.com/suhailii/OneHR-Android-MVVM/assets/74708728/1d60c89b-2ddb-4daf-95a1-24392a2c63d7" alt="Home" width="190">
<img src="https://github.com/suhailii/OneHR-Android-MVVM/assets/74708728/b998b9ec-c169-45a8-a81a-7b160aef14c7" alt="Claims" width="190">
<img src="https://github.com/suhailii/OneHR-Android-MVVM/assets/74708728/329da39b-591d-48ac-a80b-8586dcbf424b" alt="Leaves" width="190">
<img src="https://github.com/suhailii/OneHR-Android-MVVM/assets/74708728/9fe8903e-700b-4517-9c0a-9457eef9888e" alt="Jitsi" width="190">
<img src="https://github.com/suhailii/OneHR-Android-MVVM/assets/74708728/2d21919b-e0f7-4fe3-a889-68f432bf5fd9" alt="Notifs" width="190">



# Account Details:


Manager Account: 


**(Contains Manager View - Approve/Reject Claims,Leaves and Creation of Video Conferencing Room)**

Employee ID: abc
  
  
Password: 123


Employee Account:
  
  
Employee ID: suhaili 

  
Password: 123


## Contributors

The OneHR Android App was developed as a collaborative effort by the following contributors:
- Suhaili Suri ([@suhailii](https://github.com/suhailii))
- Png Han Zheng ([@ZhengZhengZhengZheng](https://github.com/ZhengZhengZhengZheng))
- Kenrick Ong ([@kenrick78](https://github.com/Kenrick78))
