# **King Me**
## *Flash cards without the paper*
*By: Forest Li*

_**King Me**_ is an application designed to help people memorize or test themselves on sets of information. This program
interests me because it is a study technique that has worked well for me in the past. Constantly buying queue cards is
annoying, so I've decided to create a computer application that serves that very purpose. This application allows for
the creation of multiple users whom can store their own private quizzes as well as a public user which everyone can
contribute to. Upon starting the application, the user will be prompted to log in, create a new users, modify a user,
delete a user, or quit. Upon logging in, the user will be prompted to a home screen where they can do the following:
make a quiz, practice a quiz, edit a quiz, view quiz, or quit. Making a quiz encompasses tabulating a set of questions 
as well as the answers for each respective question. During the testing process, the quiz will go through all the 
questions. Each question is represented as its own page where the question will be stated at the top of the page and a 
text field will be available to answer the question. Upon finishing the answer, the user can then press a button that 
will show the next question until the entire quiz is done. After the last question is answered, the application will 
show the percent of questions that were answered correctly.

## *User Stories*
- As a user, I want to be able to add questions to a quiz
- As a user, I want to be able to remove questions from a quiz
- As a user, I want to be able to edit a question in an existing quiz
- As a user, I want to be able to quiz myself with a quiz I've made

- As a user, I want to be able to save a quiz system to a file
- As a user, I want to be able to load a quiz system from a file

## *Instructions for Grader*

- You can generate the first required action related to adding a new user to the system by pressing the create new user 
button on the start menu, and create a new user.
- You can generate the second required action related to adding a question to a quiz by logging in with a user, press
the create new quiz button, press add new question button, and adding the question 
- You can locate my visual component by attempting and completing a quiz.
- You can save the state of my application by pressing the save system button in the start menu.
- You can reload the state of my application by pressing the load system button in the start menu.

## *Phase 4: Task 2*
By starting the Quiz App and pressing load system then closing the app, you should receive the following in the console:

Tue Apr 11 11:35:08 PDT 2023

User public removed from Quiz App.

Tue Apr 11 11:35:08 PDT 2023

Question if tiffanay breaks her tail bone, what effect does this have on her ability to do her math exam added to bruh.

Tue Apr 11 11:35:08 PDT 2023

Quiz bruh added to public.

Tue Apr 11 11:35:08 PDT 2023

Question 1+1 added to dankmemerquiz.

Tue Apr 11 11:35:08 PDT 2023

Quiz dankmemerquiz added to public.

Tue Apr 11 11:35:08 PDT 2023

User public added to Quiz App.

Tue Apr 11 11:35:08 PDT 2023

Question if johnny has a bouqet of 10 flowers and gives it to his crush vanessa, but he gets rejected so he begins 
crying and due to his blurry vision he now sees 20 flowers, how many flowers does he actually have? added to very 
personal quiz.

Tue Apr 11 11:35:08 PDT 2023

Question how many times does kanye east say 'fortnite balls' in his song fortnite balls added to very personal quiz.

Tue Apr 11 11:35:08 PDT 2023

Question what is long, hard and has cum in it? added to very personal quiz.

Tue Apr 11 11:35:08 PDT 2023

Question how many bitches do you have added to very personal quiz.

Tue Apr 11 11:35:08 PDT 2023

Quiz very personal quiz added to forest.

Tue Apr 11 11:35:08 PDT 2023

Question 1+1 added to dankmemerquiz.

Tue Apr 11 11:35:08 PDT 2023

Quiz dankmemerquiz added to forest.

Tue Apr 11 11:35:08 PDT 2023

User forest added to Quiz App.

Tue Apr 11 11:35:08 PDT 2023

User bob added to Quiz App.

Tue Apr 11 11:35:08 PDT 2023

Question if tiffanay breaks her tail bone, what effect does this have on her ability to do her math exam added to bruh.

Tue Apr 11 11:35:08 PDT 2023

Question 123=? added to bruh.

Tue Apr 11 11:35:08 PDT 2023

Quiz bruh added to dankmemer.

Tue Apr 11 11:35:08 PDT 2023

User dankmemer added to Quiz App.

Tue Apr 11 11:35:08 PDT 2023

User bryan added to Quiz App.

## *Refactoring*
If I had more time, I would firstly change the names of some of my getter and setter methods for classes in my model 
package so that they are consistent. For instance, when I created the "changeName" method in my quiz class, in the 
moment, it made sense to call the method "changeName" instead of "setName" because name must be set when a quiz is 
created. However, this contradicts my naming of methods in my User class such as "setUsername" and "setPassword" and 
this contradiction caused me some personal confusion while coding and likely for others if others worked on my program.

Storing questions in a list makes sense because it needs to be in chronological order, but not so much for users. When 
implementing users, the only real use for them is retrieving the object of the user and inserting it. The order of them
does not really matter, and it might make more sense to have the username as the key and the user object as the value. 
For inserting, finding and removing users, it is O(1) time complexity because all we need is the username whereas using
a list requires O(n) time complexity.