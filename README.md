Fitness Tracker App
To create a Fitness Tracker App based on the given specifications, here's how you can organize and develop the project step by step:
Project Structure:
•	Project Name: Fitness Tracker App
•	Application ID: com.example.fitnesstrackerapp
•	Minimum SDK: 25
•	Target SDK: 34
•	Programming Language: Java
________________________________________
1. Set Up Project Environment:
Steps to Create the Project in Android Studio:
1.	Open Android Studio and select New Project.
2.	Choose an Empty Activity template.
3.	Enter the Application Name as Fitness Tracker App.
4.	Set the Package Name as com.example.fitnesstrackerapp.
5.	Select Java as the programming language.
6.	Set Minimum SDK to 25 (to support a wide range of devices).
7.	Click Finish to create the project.
________________________________________
2. Key Features and Project Components:
Activity Tracking:
1.	Track Steps, Distance, and Calories:
o	Use Android's Sensor Manager for step tracking or integrate with a pedometer API.
o	Calories burned can be calculated based on activity type, user weight, and distance.
2.	Activity Selection:
o	Create an Activity Selection screen (walking, running, cycling) using a Spinner or RadioButton group.
User Profile:
1.	User Profile Creation:
o	Create an Activity for profile creation where users input name, age, weight, height.
o	Calculate BMI using the formula:

1. MainActivity (Starting Point)
•	MainActivity is the entry point of the app.
•	It contains a "Get Started" button, which when clicked, navigates the user to the LoginActivity.
 
2. Login and Sign-Up Flow
•	LoginActivity: This screen allows users to log into the app if they already have an account. The layout includes input fields for the username (or email) and password.
•	If users don’t have an account, there is an option to go to SignUpActivity.
o	SignUpActivity: Here, users can create a new account by entering their name, email, and password. Once the signup process is complete, the app redirects to HomePageActivity.
•	After successfully logging in (or signing up), the user is navigated to the HomePageActivity, where all main functionalities are located.







Login Page:                                                                                    Create Acount(Signup Page):
                           

3. HomePageActivity (Main Dashboard)
•	HomePageActivity acts as the central hub of the app. It contains a Bottom Navigation bar with three sections:
1.	HomePageActivity: Displays the dashboard with links to different fitness-related activities (BMI Calculator, Activity Tracker, Water Intake, Calories Burned).
2.	User Fragment: Handles user profile information.
3.	Notification Fragment: Manages reminders and notifications.
 
•	The Dashboard in HomePageActivity contains cards or buttons that link to various fitness features:
o	BMI Calculator (opens BMICalculatorActivity)
o	Activity Tracker (opens ActivityTrackingActivity)
o	Water Intake Tracker
o	Calories Burned Tracker
4. BMI Calculator (BMICalculatorActivity and ResultActivity)
•	BMICalculatorActivity: Allows users to calculate their Body Mass Index (BMI) by entering personal details such as:
o	Gender
o	Age
o	Height
o	Weight
•	Once the user inputs this information and clicks on the Calculate button, the app navigates to ResultActivity where the BMI result is displayed.
•	ResultActivity shows the user's BMI and, with the help of BMIUtils, provides information on whether the user is underweight, overweight, or has a normal BMI range.

 

                                                        
5. Activity Tracker (ActivityTrackingActivity)
•	ActivityTrackingActivity manages tracking of fitness activities like:
o	RunningActivity
o	JoggingActivity
o	WalkingActivity
 
•	Users can select one of these activities, set goals (e.g., time, distance), and start tracking.
•	For each activity, the screen includes:
o	Start, Pause, and Stop buttons to control the tracking session.
o	While the activity is ongoing, the app tracks and displays real-time data like:
	Distance covered
	Time spent
	Calories burned
            
•	A Graph is shown below the controls, which displays the progress made in terms of calories burned, distance, and time for each activity.
6. Water Intake and Calories Burned Tracking
•	These sections are accessible from the dashboard in HomePageActivity:
o	Water Intake Tracker: Allows users to set daily water consumption goals and track their intake.
o	Calories Burned Tracker: Tracks the calories burned throughout different activities over the day and presents progress in charts.
7. Notifications and Reminders (NotificationFragment and AddReminderActivity)
•	NotificationFragment: Displays a list of notifications for the user (e.g., reminders for fitness activities or water intake).
•	Users can add reminders by clicking on a plus icon. This opens the AddReminderActivity, where they can:
o	Set a time and date for the reminder.
o	Choose the type of reminder (e.g., running, walking, water intake, jogging).
•	These reminders are saved and displayed in a RecyclerView in the NotificationFragment.
                      

8. User Profile (UserFragment)
•	UserFragment manages the user’s profile.
•	Users can view and edit their personal information (name, age, weight, height).
•	After clicking an Edit button, they can update their details and save the changes.
•	There is also a Sign-Out button here that logs the user out and redirects them back to the LoginActivity.
                                       



9. Data Persistence and Storage
•	The app stores the user’s profile data and fitness activity logs using either an SQLite database or Room, which helps ensure the app can function offline and retain user data.
•	User information such as BMI results, daily steps, distance, and calories are stored in the database for future reference.
•	Progress data (steps, distance, calories) is also shown in various charts (daily, weekly, and monthly) on the progress page.
 
 
10. Reminders and Alarms
•	Reminders and notifications are handled using AlarmManager or WorkManager.
•	The app sends reminders for fitness activities like running or water intake based on the time and type of activity set by the user in AddReminderActivity.


11. App Structure Overview (Key Files)
•	MainActivity: The launch screen with a "Get Started" button that leads to login.
•	LoginActivity & SignUpActivity: Handles user authentication.
•	HomePageActivity: The central hub with bottom navigation and dashboard.
•	BMICalculatorActivity & ResultActivity: For BMI calculation and result display.
•	ActivityTrackingActivity: Tracks running, jogging, and walking, showing goals and progress.
•	NotificationFragment & AddReminderActivity: Displays notifications and allows users to set reminders.
•	UserFragment: Manages user profile and allows for edits and signing out.
https://github.com/user-attachments/assets/c757ef57-a97c-4995-b851-871f7ed48835
