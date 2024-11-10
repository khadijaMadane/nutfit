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

NutFit uses the **two-phase method** to solve the linear optimization problem. The first phase finds an initial feasible solution, and the second phase optimizes this solution to minimize feed ration costs.

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

## Contributions

Contributions are welcome! If you have any improvement ideas, bug fixes, or suggestions, feel free to submit a **pull request** or open an **issue** in the repository.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.
