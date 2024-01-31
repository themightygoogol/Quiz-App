package ui;

import model.Question;
import model.Quiz;
import model.QuizSystem;

import model.User;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Objects;


// Quiz application
public class QuizApp {
    private static final String JSON_STORE = "./data/quizSystem.json";
    private QuizSystem quizSystem;
    private User user;
    private User publicUser;
    private Scanner input;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;

    // EFFECTS: runs the quiz application
    public QuizApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        quizSystem = new QuizSystem(new ArrayList<>());
        runStartMenu();
    }

    // EFFECTS: processes user input
    private void runStartMenu() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            displayStartMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processStartMenuCommand(command);
            }
        }
        System.out.println("\nGoodbye!");
    }

    // EFFECTS: processes user input
    private void processStartMenuCommand(String command) {
        switch (command) {
            case "k":
                runUserMenu();
                break;
            case "c":
                createUser();
                break;
            case "m":
                modifyUser();
                break;
            case "d":
                deleteUser();
                break;
            case "l":
                loadUsers();
                break;
            case "s":
                saveSystem();
                break;
            default:
                System.out.println("Selection not valid...");
                break;
        }
    }


    // EFFECTS: displays the start menu options to the user
    private void displayStartMenu() {
        System.out.println("\nWelcome to quiz app. Please select from the following: ");
        System.out.println("\tk -> select user");
        System.out.println("\tc -> create user");
        System.out.println("\tm -> modify user");
        System.out.println("\td -> delete user");
        System.out.println("\tl -> load system");
        System.out.println("\ts -> save system");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: prompts user to select a valid user in the quiz system to log in with
    private void runUserMenu() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            System.out.println("Select the user you would like to login with or 'q' to bo back.");
            displayUsers();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                user = quizSystem.findUser(command);
                if (user == null) {
                    System.out.println("Selection not valid...");
                } else {
                    processUserMenuCommand(command);
                    keepGoing = false;
                }
            }
        }
    }

    // EFFECTS: displays all the users in the quiz system
    private void displayUsers() {
        if (quizSystem.getNumberOfUsers() == 0) {
            System.out.println("There are no users in the system.");
        } else {
            for (int i = 0; i < quizSystem.getNumberOfUsers(); i++) {
                System.out.println(quizSystem.getUser(i).getUsername());
            }
        }
    }

    // REQUIRES: command must be the name of a user in teh quiz system
    // EFFECTS: runs the appropriate course of action for the selected user
    private void processUserMenuCommand(String command) {
        user = quizSystem.findUser(command);

        if (user.getUsername().equals("public")) {
            runMainMenu();
        } else {
            runLoginMenu();
        }
    }

    // EFFECTS: prompts the user to enter the password for the selected user or go back
    private void runLoginMenu() {
        boolean keepGoing = true;
        boolean successful;
        String command;

        init();

        while (keepGoing) {
            System.out.println(user.getUsername() + " selected. Please enter the password or 'q' to go back.");
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                successful = processPasswordAttempt(command);
                if (successful) {
                    keepGoing = false;
                }
            }
        }
    }

    // EFFECTS: runs main menu and returns true if password attempt is correct, returns  false otherwise
    private boolean processPasswordAttempt(String command) {
        if (user.getPassword().equals(command)) {
            runMainMenu();
            return true;
        } else {
            System.out.println("Incorrect password, please try again... ");
            return false;
        }
    }

    // MODIFIES: quizSystem
    // EFFECTS: creates a new user in the quiz system
    @SuppressWarnings("methodlength")
    private void createUser() {
        boolean keepGoing = true;
        String command;
        String username;
        String password;
        User user;

        init();

        while (keepGoing) {
            System.out.println("Enter the name of this user of 'q' to quit.");
            command = input.next();
            command = command.toLowerCase();


            if (command.equals("q")) {
                keepGoing = false;
            } else if (quizSystem.findUser(command) == null) {
                username = command;
                System.out.println("What is the password for " + username + "?");
                command = input.next();
                command = command.toLowerCase();
                password = command;
                user = new User(username, password, new ArrayList<>());
                quizSystem.addUser(user);
                System.out.println("User " + username + " has been created!");
                keepGoing = false;
            } else {
                System.out.println("Invalid entry, please try again...");
            }
        }
    }

    // MODIFIES: user
    // EFFECTS: prompts user to modify a user's username and password
    private void modifyUser() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            System.out.println("Enter the name of the user you would like to modify or 'q' to quit.");
            displayUsers();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else if (command.equals("public")) {
                System.out.println("You cannot modify the public account!");
            } else if (!(quizSystem.findUser(command) == null)) {
                if (verifyUser(quizSystem.findUser(command))) {
                    changeUsername(quizSystem.findUser(command));
                    changePassword(quizSystem.findUser(command));
                }
                keepGoing = false;
            } else {
                System.out.println("A user with this name already exists, try another name!");
            }
        }
    }

    // MODIFIES: User
    // EFFECTS: prompts user to update the username for the imputed user
    private void changeUsername(User user) {
        boolean keepGoing = true;
        String selection;

        init();

        System.out.println("Would you like to change the username? 'y' or 'n'.");
        while (keepGoing) {
            selection = input.next();
            selection = selection.toLowerCase();
            if (selection.equals("y")) {
                System.out.println("What would you like to change the username to?");
                selection = input.next();
                selection = selection.toLowerCase();
                user.setUsername(selection);
                System.out.println("Username successfully updated!");
                keepGoing = false;
            } else if (selection.equals("n")) {
                keepGoing = false;
            } else {
                System.out.println("Invalid selection, please try again...");
            }
        }
    }

    // MODIFIES: User
    // EFFECTS: prompts the user to update the password for the imputed user
    private void changePassword(User user) {
        boolean keepGoing = true;
        String selection;

        init();

        System.out.println("Would you like to change the password? 'y' or 'n'.");
        while (keepGoing) {
            selection = input.next();
            selection = selection.toLowerCase();
            if (selection.equals("y")) {
                System.out.println("What would you like to change the password to?");
                selection = input.next();
                selection = selection.toLowerCase();
                user.setPassword(selection);
                System.out.println("Password successfully updated!");
                keepGoing = false;
            } else if (selection.equals("n")) {
                keepGoing = false;
            } else {
                System.out.println("Invalid selection, please try again...");
            }
        }
    }

    // EFFECTS: prompts user to log in and returns whether a user can successfully do so
    private boolean verifyUser(User userToVerify) {
        boolean keepGoing = true;
        String command;
        while (keepGoing) {
            System.out.println("Enter the current password for " + userToVerify.getUsername() + " or 'q' to quit.");
            command = input.next();
            command = command.toLowerCase();


            if (command.equals("q")) {
                return false;
            } else if (userToVerify.getPassword().equals(command)) {
                return true;
            } else {
                System.out.println("Invalid entry, please try again...");
            }
        }
        return false;
    }


    // MODIFIES: quizSystem
    // EFFECTS: removes user with imputed username in the quiz system
    @SuppressWarnings("methodlength")
    private void deleteUser() {
        boolean keepGoing = true;
        String command;
        String username;

        init();
        if (quizSystem.getNumberOfUsers() == 1) {
            System.out.println("There are no deletable users");
        } else {
            while (keepGoing) {
                System.out.println("Users: ");
                displayUsers();
                command = input.next();
                command = command.toLowerCase();
                System.out.println("Enter the user you would like to delete or 'q' to go back.");

                if (command.equals("q")) {
                    keepGoing = false;
                } else if (command.equals("public")) {
                    System.out.println("Cannot delete the public account!");
                } else if (!(quizSystem.findUser(command) == null)) {
                    username = command;
                    System.out.println("Are you sure you want to delete " + username
                            + "? Enter 'y' for yes and 'n' for no.");
                    command = input.next();
                    command = command.toLowerCase();
                    if (command.equals("y")) {
                        quizSystem.removeUser(username);
                        System.out.println(username + " has been successfully deleted.");
                    }
                } else {
                    System.out.println("Invalid user, please try again...");
                }
            }
        }
    }


    // EFFECTS: processes user input
    private void runMainMenu() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            displayMainMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processMainMenuCommand(command);
            }
        }

        if (user.getUsername().equals("public")) {
            System.out.println("See you later!");
        } else {
            System.out.println("\nSee you later " + user.getUsername() + "!");
        }
    }

    // EFFECTS: processes user command
    private void processMainMenuCommand(String command) {
        switch (command) {
            case "c":
                createQuiz();
                break;
            case "m":
                modifyQuiz();
                break;
            case "v":
                viewQuiz();
                break;
            case "t":
                takeQuiz();
                break;
            default:
                System.out.println("Selection not valid...");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes scanner
    private void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays main menu of options to user
    private void displayMainMenu() {
        System.out.println("Welcome to the main menu " + user.getUsername());
        System.out.println("\nSelect from:");
        System.out.println("\tc -> create a new quiz");
        System.out.println("\tm -> modify an existing quiz");
        System.out.println("\tv -> to view an existing quiz");
        System.out.println("\tt -> take a quiz");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: displays all the quizzes made so far
    private void displayQuizzes() {
        if (user.getNumberOfQuizzes() == 0) {
            System.out.println("There are no quizzes!");
        } else {
            System.out.println("==============================================================");
            System.out.println("Quizzes:");
            for (int i = 0; i < user.getNumberOfQuizzes(); i++) {
                System.out.println((i + 1) + ". " + user.getQuiz(i).getName());
            }
            System.out.println("==============================================================");
        }
    }

    // EFFECTS: displays all the questions made so far in the quiz
    public void displayQuestions(Quiz quiz) {
        if (quiz.getNumberOfQuestions() == 0) {
            System.out.println("No questions in the quiz!");
        }
        System.out.println("==============================================================");
        System.out.println("Questions:");
        for (int i = 0; i < quiz.getNumberOfQuestions(); i++) {
            System.out.println("Question " + (i + 1) + ":");
            System.out.println("Q: " + quiz.getQuestion(i).getQuestion());
            System.out.println("A: " + quiz.getQuestion(i).getAnswer());
        }
        System.out.println("==============================================================");
    }

    // MODIFIES: this
    // EFFECTS: prompts user to create a quiz or quit
    @SuppressWarnings("methodlength")
    private void createQuiz() {
        String selection;
        Quiz quiz;
        System.out.println("Enter the name of the quiz or 'q' to quit");
        selection = input.next();
        selection = selection.toLowerCase();
        boolean keepGoing = true;

        if (!(selection.equals("q"))) {
            quiz = runQuestionLoop(selection);
            if (!user.getUsername().equals("public")) {
                System.out.println("Would you like to make this quiz public? 'y' or 'n'.");

                while (keepGoing) {
                    selection = input.next();
                    selection = selection.toLowerCase();
                    if (selection.equals("y")) {
                        publicUser.addQuiz(quiz);
                        user.addQuiz(quiz);
                        System.out.println("The Quiz: '" + quiz.getName()
                                + "' has been successfully been created and made public.");
                        keepGoing = false;
                    } else if (selection.equals("n")) {
                        user.addQuiz(quiz);
                        System.out.println("The Quiz: '" + quiz.getName()
                                + "' has been successfully been created and made private.");
                        keepGoing = false;
                    } else {
                        System.out.println("Invalid selection, please try again...");
                    }
                }
            } else {
                user.addQuiz(quiz);
                System.out.println("The Quiz: '" + quiz.getName()
                        + "' has been successfully been created.");
            }
        }
    }


    // EFFECTS: makes changes to an existing quiz within quizzes
    private void modifyQuiz() {
        String selection;
        boolean keepGoing = true;

        while (keepGoing) {
            displayQuizzes();
            System.out.println("Enter the name of the quiz you would like to modify or 'q' to quit");
            selection = input.next();
            selection = selection.toLowerCase();

            if (selection.equals("q")) {
                keepGoing = false;
            } else if (user.getQuiz(selection) == null) {
                System.out.println("Invalid entry, please try again...");
            } else {
                editQuiz(user.getQuiz(selection));
                keepGoing = false;
            }
        }
    }

    // MODIFIES: imputed quiz
    // EFFECTS: edits imputed quiz with updated contents
    private void editQuiz(Quiz quiz) {
        String selection;
        boolean keepGoing = true;

        while (keepGoing) {
            displayModifyMenu();
            selection = input.next();
            selection = selection.toLowerCase();

            if (selection.equals("m")) {
                modifyQuestion(quiz);
            } else if (selection.equals("a")) {
                addQuestion(quiz);
            } else if (selection.equals("r")) {
                removeQuestion(quiz);
            } else if (selection.equals("s")) {
                changeStatus(quiz);
            } else if (selection.equals("q")) {
                keepGoing = false;
            } else {
                System.out.println("Selection not valid...");
            }
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayModifyMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tm -> modify an existing question");
        System.out.println("\ta -> add a question");
        System.out.println("\tr -> remove a question");
        System.out.println("\ts -> change status of quiz");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: quiz
    // EFFECTS: prompts user to change a quiz from public to private and vice versa
    @SuppressWarnings("methodlength")
    private void changeStatus(Quiz quiz) {
        String selection;
        boolean keepGoing = true;

        if (publicUser.getQuiz(quiz.getName()) == null) {
            System.out.println("This quiz is currently private. Would you like to make it public? 'y' or 'n':");
            while (keepGoing) {
                selection = input.next();
                selection = selection.toLowerCase();
                if (selection.equals("y")) {
                    publicUser.addQuiz(quiz);
                    System.out.println("Quiz '" + quiz.getName() + "' has successfully been made public!");
                    keepGoing = false;
                } else if (selection.equals("n")) {
                    keepGoing = false;
                } else {
                    System.out.println("Selection not valid, please try again...");
                }
            }
        } else {
            System.out.println("This quiz is currently public. Would you like to make it private? 'y' or 'n':");
            while (keepGoing) {
                selection = input.next();
                selection = selection.toLowerCase();
                if (selection.equals("y")) {
                    publicUser.removeQuiz(quiz.getName());
                    System.out.println("Quiz '" + quiz.getName() + "' has successfully been made private!");
                    keepGoing = false;
                } else if (selection.equals("n")) {
                    keepGoing = false;
                } else {
                    System.out.println("Selection not valid, please try again...");
                }
            }
        }
    }



    // EFFECTS: prints out all the questions and answers in the quiz the user chooses
    private void viewQuiz() {
        Quiz quiz;
        String selection;
        boolean keepGoing = true;

        while (keepGoing) {
            displayQuizzes();
            System.out.println("Enter the name of the quiz you would like to view or 'q' to quit ");
            selection = input.next();
            selection = selection.toLowerCase();

            if (selection.equals("q")) {
                keepGoing = false;
            } else {
                if (user.getQuiz(selection) == null) {
                    System.out.println("Selection not valid...");
                } else {
                    quiz = user.getQuiz(selection);
                    assert quiz != null;
                    displayQuestions(quiz);
                }
            }
        }
    }


    // EFFECTS: prompts user to go answer the questions in the quiz in chronological order
    @SuppressWarnings("methodlength")
    private void takeQuiz() {
        int numberOfCorrectAnswers;
        boolean keepGoing = true;
        String selection;

        while (keepGoing) {
            displayQuizzes();
            System.out.println("Enter the name of the quiz you would like to try or 'q' to quit ");
            numberOfCorrectAnswers = 0;
            selection = input.next();
            selection = selection.toLowerCase();

            if (selection.equals("q")) {
                keepGoing = false;
            } else {
                if (user.getQuiz(selection) == null) {
                    System.out.println("Selection not valid...");
                } else {
                    Quiz quiz = user.getQuiz(selection);
                    for (int i = 0; i < Objects.requireNonNull(quiz).getNumberOfQuestions(); i++) {
                        boolean result = tryQuestion(quiz.getQuestion(i));
                        if (result) {
                            numberOfCorrectAnswers += 1;
                        }
                    }
                    System.out.println("You got " + numberOfCorrectAnswers + " out of " + quiz.getNumberOfQuestions()
                            + "questions right.");
                }
            }
        }
    }

    // EFFECTS: returns whether the user has imputed the correct answer to the corresponding question
    private boolean tryQuestion(Question question) {
        System.out.println(question.getQuestion());
        System.out.println("Answer: ");
        String answer = input.next();
        return answer.equals(question.getAnswer());
    }

    // MODIFIES: quiz
    // EFFECTS: modifies question(s) in imputed quiz
    private void modifyQuestion(Quiz quiz) {
        boolean questionExists;
        String newQuestion;
        String newAnswer;

        displayQuestions(quiz);
        System.out.println("Enter the question number you would like to modify or 'q' to quit");
        String selection = input.next();
        if (!(selection.equals("q") || selection.equals("Q"))) {
            int index = Integer.parseInt(selection);
            index -= 1;
            questionExists = quiz.indexExistsInQuestions(index);
            if (!questionExists) {
                System.out.println("Selection not valid...");
            } else {
                System.out.println("Please enter the new question: ");
                newQuestion = input.next();
                System.out.println("Please enter the new answer: ");
                newAnswer = input.next();
                quiz.modifyQuestion(index, newQuestion, newAnswer);
                System.out.println("Question modified!");
            }
        }
    }

    // MODIFIES: quiz
    // EFFECTS: adds question(s) in imputed quiz
    private void addQuestion(Quiz quiz) {
        String selection;

        System.out.println("Enter the question you would like to add or 'q' to quit");
        selection = input.next();
        if (!(selection.equals("q") || selection.equals("Q"))) {
            System.out.println("Enter the answer for the question or 'q' to cancel");
            String answer = input.next();
            if (!(answer.equals("q") || answer.equals("Q"))) {
                Question nextQuestion = new Question(selection, answer);
                quiz.addQuestion(nextQuestion);
                System.out.println("Question added!");
            }
        }
    }

    // EFFECTS: removes question(s) in imputed quiz
    private void removeQuestion(Quiz quiz) {
        String selection;
        boolean questionExists;

        displayQuestions(quiz);
        System.out.println("Enter the question number you would like to remove or 'q' to quit");
        selection = input.next();
        if (!(selection.equals("q") || selection.equals("Q"))) {
            int index = Integer.parseInt(selection);
            index -= 1;
            questionExists = quiz.indexExistsInQuestions(index);
            if (!questionExists) {
                System.out.println("Selection not valid...");
            } else {
                quiz.removeQuestion(index);
                System.out.println("Question removed!");
            }
        }
    }

    // REQUIRES: non-empty name
    // EFFECTS: prompts user to continue adding questions to the quiz until they choose to quit
    @SuppressWarnings("methodlength")
    private Quiz runQuestionLoop(String name) {
        Quiz quiz = new Quiz(name);
        boolean keepGoing = true;
        String command;
        String answer;
        Question nextQuestion;

        while (keepGoing) {
            System.out.println("Enter the next question or 'q' if you're done adding questions.");
            command = input.next();
            command = command.toLowerCase();
            if (command.equals("q")) {
                keepGoing = false;
            } else {
                System.out.println("Enter the answer for the question or 'q' to cancel");
                answer = input.next();
                answer = answer.toLowerCase();
                if (answer.equals("q")) {
                    break;
                } else {
                    nextQuestion = new Question(command, answer);
                    quiz.addQuestion(nextQuestion);
                    System.out.println("Question added!");
                }
            }
        }
        return quiz;
    }

    // EFFECTS: saves the quizSystem to file
    private void saveSystem()  {
        try {
            jsonWriter.open();
            jsonWriter.write(quizSystem);
            jsonWriter.close();
            System.out.println("All changes saved");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads quizSystem from file
    public void loadUsers() {
        try {
            quizSystem = jsonReader.read();
            publicUser = quizSystem.findUser("public");

            System.out.println("Loaded quiz app system information from: " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}