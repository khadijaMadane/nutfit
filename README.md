# NutFit

**NutFit** is a feed formulation application that aims to optimize cost while meeting required nutritional goals. By leveraging operational research techniques and modern tools like **Java**, **Kotlin**, and **Firebase**, NutFit allows users to generate balanced feed rations in a practical and efficient manner.

## Project Overview

NutFit aims to provide an innovative solution for calculating and formulating optimized feed rations. Using the **two-phase method** from operational research, the application calculates the optimal combination of ingredients to meet nutritional goals at the lowest cost.

NutFit is developed in **Java** for backend logic and **Kotlin** for the mobile user interface, while using **Firebase** for real-time data management.

## Features

- **Feed Formulation**: Calculates a balanced feed ration based on the user's nutritional needs.
- **Cost Optimization**: Minimizes the total cost of ingredients while adhering to nutritional constraints.
- **Data Tracking**: Stores user and formulation data in Firebase for centralized management.
- **Intuitive Interface**: Simple, user-friendly mobile interface for easy navigation through options.

## Architecture and Technologies

NutFit relies on a mobile architecture with the following components:
- **Java**: Used for the development of the optimization algorithm.
- **Kotlin**: Primary language for the Android mobile app development.
- **Firebase**: Real-time database for storing user data and nutritional parameters.

## Optimization Model

NutFit employs a **two-phase method** to solve linear optimization problems:

1. **Phase One**: Finds a feasible solution that satisfies all nutritional constraints.
2. **Phase Two**: Optimizes the solution to minimize the total ration cost.

### Optimization Model Steps

1. **Variable Definition**: Each ingredient is represented by a variable indicating the quantity to include in the ration.
2. **Objective Function**: Minimize the total cost of ingredients.
3. **Nutritional Constraints**: Ensure that the ration provides the minimum required amounts of each nutrient.
4. **Optimal Solution**: Calculate the combination of ingredients that minimizes cost while meeting constraints.

## Usage

1. **Sign Up / Log In**: Register or log in to the app.
2. **Input Nutritional Data**: Enter specific nutritional requirements or select predefined needs.
3. **Calculate Optimal Ration**: The app generates an optimized ration that minimizes cost.
4. **Save and View Rations**: Save results in Firebase and access your ration history.
---

## Getting Started

Follow these steps to set up the NutFit project on your local machine using **Android Studio**.

### Step 1: Clone the NutFit Project from GitHub

1. Open **Android Studio** and navigate to **File > New > Project from Version Control**.
2. Select **Git** and enter the GitHub repository URL:
    ```bash
    https://github.com/KhadijaMadane/NutFit.git
    ```
3. Click **Clone** and wait for the project files to download to your local machine.

#### Alternative Method:

You can also clone the repository using the command line:

```bash
git clone https://github.com/KhadijaMadane/NutFit.git  
```
Then, open the project in Android Studio by selecting **File > Open** and navigating to **the NutFit folder**.

### Step 2: Set Up Firebase for NutFit (Firestore)

Follow these steps to set up Firebase Firestore for NutFit:

1. **Go to the Firebase Console**:
   - Navigate to [Firebase Console](https://console.firebase.google.com/).
   - Create a new Firebase project or select an existing one.

2. **Add an Android App to Firebase**:
   - Register your app with the **package name** found in `app/build.gradle`.
   - Download the `google-services.json` file provided by Firebase and place it in the `app/` directory of your NutFit project.

3. **Enable Firestore**:
   - In the Firebase Console, navigate to **Firestore Database**.
   - Click on **Create Database**.
   - Choose **Start in Test Mode** (for initial testing) and enable Firestore.

---

### Step 3: Configure Firebase Authentication

Follow these steps to enable Firebase Authentication:

1. **Go to Firebase Authentication**:
   - In the Firebase Console, navigate to **Authentication** and click on **Get Started**.

2. **Enable Sign-in Methods**:
   - Choose the sign-in methods you want to use for authentication (e.g., **Email/Password** or **Google Sign-In**).

3. **Add Firebase Authentication Library**:
   - In your Android project, open the `build.gradle` file (app-level) and ensure the following dependency is added:
   
     ```gradle
     implementation 'com.google.firebase:firebase-auth:22.0.0'
     ```

4. **Sync Project**:
   - Click **Sync Now** in Android Studio to sync the Firebase Authentication library with your project.

---

### Step 4: Build and Run the Application

To build and run the NutFit application:

1. **Set Up Your Android Device or Emulator**:
   - Make sure your Android device is connected or set up an Android Emulator.

2. **Build the Application**:
   - In Android Studio, click the **Run** button (green play icon) or go to **Build > Build Bundle(s) / APK(s) > Build APK(s)**.

3. **Launch the App**:
   - Once the APK is built, the app will automatically install and launch on your connected device or emulator.

---

### Step 5: Testing the Application

Test the main features of the NutFit application:

1. **Authentication**:
   - Test the authentication flow by signing up or logging in using the enabled sign-in methods.

2. **Firestore Data Input**:
   - Input nutritional data or use predefined nutritional requirements within the app.

3. **Optimized Ration Calculation**:
   - Use the app to generate a cost-effective ration that meets the specified nutritional requirements.

4. **Data Storage and Retrieval**:
   - Verify that the generated rations are saved in Firestore and can be accessed or retrieved.

5. **Data Management**:
   - Test the app’s interface for viewing and managing saved rations and user data.

By following these steps, you will have Firebase set up for authentication and data storage, and your NutFit application will be ready to use for generating optimized feed rations.
## Contributors

This project was developed and maintained by:

- **Khadija Ben Madane**
- **Imane Ougni**


## Contributions

Contributions are welcome! If you have any improvement ideas, bug fixes, or suggestions, feel free to submit a **pull request** or open an **issue** in the repository.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

