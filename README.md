# CyberSpeedGame

CyberSpeedGame is a Java scratch game, that will generate a matrix (for example 3x3) from symbols(based on probabilities
for each individual cell) and based on winning combintations user either will win or lost.
User will place a bet with any amount which we call *betting amount* in this assignment.

## Prerequisites

To run this project, ensure that you have the following installed on your machine:

- [Gradle](https://gradle.org/install/) (version 7.0 or later recommended)
- [JDK >= Java 8](https://openjdk.java.net/install/) (this project was developed and tested with Java 22)

## Getting Started

Follow the instructions below to build and run the project.

### Cloning the Repository

1. Clone the repository to your local machine:
   ```bash
   git clone https://github.com/DevilDeveloper7/CyberSpeedGame.git
   cd CyberSpeedGame
   ```

2. Build the project using Gradle:
   ```bash
   ./gradlew fatJar
   ```

   This command compiles the source code and packages it into a JAR file. You can find the resulting JAR file at:
   ```bash
   cd build/libs/CyberSpeedGame.jar
   ```
3. Running the Application

   To start the application, use the following command:
    ```bash
    java -jar <yourPath>'/build/libs/CyberSpeedGame.jar --config <yourPath>/config.json --betting-amount 100
   ```

**Replace \<yourPath\> with the path to the directory containing CyberSpeedGame.jar and directory with config.**

## Configuration

The application requires a configuration file named config.json for proper execution. Ensure that this file is present
in the working directory.
