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
