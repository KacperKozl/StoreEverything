# StoreEverything 📝

![StoreEverything data](/Image/StoreEverything.png?raw=true "StoreEverything")

<p align="justify">
Application description: <br>
The application is used to save and share valuable information, such as websites, shopping lists or meeting invitations, in the form of notes. Its main purpose is to allow users to save interesting content in their accounts, share it with others and conveniently browse and search at a convenient time.

Info data: title (3-20), content (5-500), [link], date added (current, format dd-mm-yyyy), 
[reminder date], category
Category data: name (3-20, small letters)
User data: first name (3-20, letters, first capital), surname (3-50, letters, first capital), login(3-20, lowercase), password (at least 5 characters), age (min. 16 years)


User roles: 

   admin <br>
    - manages users,
    
   limited user<br>
     - is registered, can view shared links, but cannot create information
     
   full user<br>
     - can create information and share it with others, view information made available to       
     him/her in a separate view
     
   not logged in user<br>
     - has access only to the start page and the registration page


<br>
Detailed functionalities, Full user:

  ● Adding/editing/deleting the information you have collected
  
  ● Form validation
  
  ● Editing on live data
  
  ● Adding a new category
  
  ● Display shared by other information
  
  ● sharing : with an indication of the user's use or in a link
  
  ● Displaying "your" information: sorting in both procedures (data, category, alphabetically)
  
  ● Memorization of criteria and sorting criteria
  
  ● Filtering by date (from the current one) and category (from the most popular)
  
  ● Login
  
  ● Reminder recording information compatible with the actual display function in a special view
    that will appear immediately after login
    
  ● If there is no such information or its reminder dates are different from the actual data, this view is not presented


<br>
Detailed functionalities, not logged in:

  ● Registration
  
  ● Form validation
  
  ● Welcome page
  
  ● Display information from a shared link

  
<br>
Detailed functionalities, admin:

  ● Display user lists
  
  ● Role management


<br>
Technical elements:

  ● Controllers
  
  ● Database (at least 2 tables with relation)
  
  ● Views: validated forms (3 different elements), 5 different Thymeleaf tags
  
  ● Session
  
  ● Cookies
  
  ● REST service (to verify the category name in the dictionary)
  
  ● Spring Security (with database)


<br>
Detailed functionalities for a user with full rights (full user):<br>
1. Adding/editing/deleting information:
Files: ItemsController, NotesEntityRepository, dbService, NotesEntity, Note

  ● Adding information: The add() and addItems() functions in ItemsController. The add() function adds the required attributes to the model and returns the 
   add_item.html view. The request from the add_item.html view is redirected to addItems(), with an object with note information, if it fails validation, the 
   same view is returned again. After passing the validation, a new note is added with the dbService.addNewNote() function. Information not provided by the user 
   is also added to it, such as the date of addition or user_id. dbService.addNewNote() converts from a validated object to a database Entity and then saves it.
 
  ● Deleting information: In ItemsController, deleteNote() function. According to the note ID given as the attribute, any related records from the shared notes 
   are deleted, and then the note is deleted from the NotesEntityRepository, and thus the database using JPA methods.
 
  ● Edit: In ItemsController addNote() functions. The function mapped to /items/edit/init takes care of adding the required attributes to the model so that the 
   note information can be edited. The flow of events is very similar to adding information, but the corresponding view is populated with information about the 
   edited note. Validation of data occurs in the same way, and after passing validation, changes are saved.

3. Form Validation:
Files: Note, Category, …

  ● Validation of forms is carried out using appropriate classes and annotations to fields in the class, which, when the data does not meet the requirements, 
   errors are created in the object of the BindingResult class. If an error is found, we send back to the view with the form and display an error message.

5. Editing on live data:
File: ItemsController, view edit_note.html
 
  ● In the edit_note.html view, the fields contain the data added as an attribute of the note. Described in the edit note.

6. Adding a new category:
Files: ItemsController,CategoryEntityRepository, dbService, CategoryEntity, Category

● In ItemsController, addCategory() functions. The addCategory() function that maps GET requests adds the required attributes to the model and returns the 
   add_category.html view. The request from the add_Category.html view is redirected to addCategory (POST), with the Category object to be added, if it fails    
   validation, the same view is returned again. An additional condition for passing the validation is that there is no already created category with the given    
   name. In addition, using the REST service, we do not allow the category name to be a numeral. After passing validation, a new category is added in       
   CategoryEntityRepository.save().

8. View shared information:
Files: shared_link.html, shared_mine.html, ItemsController, shared_link.html
 
  ● In the link: The showSharedNote function handles requests(/items/shared/{id}) that contain the note number as a parameter. The note number is encrypted so 
   that each note cannot be easily viewed. Encryption in services/crypto.
 
  ● Shared with this user: showSharedToMe function, handles requests(/items/shareto/mine). The function retrieves the user's login and based on the login,  only 
   notes shared with this user are searched, which are then displayed in the shared_mine.html view

9. Providing information:
Files: ItemsController, shared_to.html
 
  ● After selecting the “share” option, the note is redirected to /items/shareto, function shareNoteTo(). As a model attribute, the function receives the index 
   of the note to be shared. The index is encrypted and a special link is created. The link is displayed in the shared_to.html view. This view lists the users   
   that the note can be personally shared with after clicking a button and creating a request. The dbShare() function handles a request where the index of the 
   note and the user with whom the note needs to be shared is received. After that, a database entry is created and you are returned to the notes page.

10. Displaying "your" information:
  
  ● User can sort notes alphabetically, by date added, categories, popular categories or reminder date in both directions. The implementation is in index.html, 
   ItemsController.java and NotesEntityRepository.java: showItems() function (maps /items/sortby) Based on the special object SortIndex, where the sorting 
   parameters are located, the appropriate find() method from the repository is called and found notes are added as an attribute, a cookie is created with the 
   sort data, and then the index.html view is displayed with the basic notes view.

11. Saving directions and sorting criteria:

  ● The application remembers the user's preferences for sorting information using cookies. Implementation in ItemsController.java

12. Filter by date and category:
Files: ItemsController
  
  ● FilterByCategory() and filterByDate() handle the filtering. They accept a category or a date as the sent attribute, respectively, then the appropriate data 
   is retrieved from the repositories, based on the received attribute, which is displayed as in the case of standard display of all notes.

13. Login:
Files: login.html, User.java, UsersAuthDBService.java
 
  ● The user can login to the application to access his account. The user logs in with a login and password.

14. Saving to the database when logging out/session expires:
  
  ● Only the use of the basic JPA system (save() function), where the tables are updated in the database only when some data in them is needed. Additionally, 
   flush(), at logout.

15. Displaying information with the reminder date in line with the current date:

  ● Files:Items Controller and today.html
  The initial redirect after login takes you to /items/today, which is handled by the today() function in the ItemsController. If notes with today's date are 
 found, a special view with the corresponding notes is shown, otherwise we are redirected to the start page.


<br>
Detailed functionalities for an unregistered user:<br>
1. Registration:
 
  ● The user can register in the application by providing the required data. The registration is in the register.html and DefaultUserService.java file that 
   implements the UserService interface.

2. Form Validation:

  ● Registration forms are validated to ensure that the data entered is correct and meets the requirements. The mechanism is the same as for adding notes and 
   categories. The login must be unique and the entered passwords (password and repeatPassword) must match.

3. Welcome page:

  ● The welcome page contains a welcome message and a login and registration button. The welcome page is in the startpage.html file.

4. Displaying information from the shared link:

  ● Files: ItemsController and shared_link.html
  
  ● The showSharedNote function handles requests(/items/shared/{id}) that include the note number as a parameter. The note number is encrypted so that each note 
   cannot be easily viewed. Encryption in services/crypto.


<br>
Detailed functionalities for the administrator:<br>
1. Displaying the list of users:

  ● The administrator can view the list of registered users in the “Manage users” tab - /users

2. Role management:

  ● The administrator can manage user roles, e.g. grant full permissions or restrict rights.


<br>
Technical elements:<br>

1. Controllers:

  ● The application contains three controllers - AuthController.java, which handles user authorization requests and the welcome page, ItemsController.java, which 
   handles most requests related to the functionalities of logged-in users, and UsersController.java, which handles requests related to user management by the 
   administrator.

2. Database:

  ● The application uses the H2 database, which stores information about users, notes and categories. The database is in the data/db.mv.db file.

3. Views:

  ● The application contains various views, including validated forms and various Thymeleaf tags, which are used to render data. resources/templates directory.

4. Session:
 
    ● The application uses the session to track information about the logged-in user and maintain the state of the application.
 
    ● To properly transfer information from the cookie (creating a special object with sorting data to be transferred) and to avoid code redundancy, we also used 
   RedirectAttributes and FlashAttribute, which are located in the session.

5. Cookies:

  ● The application uses cookies to store data on search parameters on the user's computer. The implementation is in ItemsController.java functions start and 
   showItems (mapping /items/ and /items/sortby.

6. REST service:
Files: ItemsController, RestNameService

  ● The application provides a REST service that prevents the category name from being a numeral.
  
  ● The service required us to create a separate application in order to work, which we will describe at the end.
  
  ● A rest object of the RestNameService class is created in the ItemsController file. Then, in the addCategory() method mapping POST requests using the   
   isNameNotNumeral() method of the rest object, a separately created application running on "http://localhost:8090/" checks whether the category name is not a 
   numeral. When this condition is passed correctly, a new category is created.

7.Spring Security:
Directory: security
 
  ● The application uses Spring Security to manage user authentication and permissions.


<br>
Visual aspect:<br>
  1. Compliant with WCAG 2.1 standard<br>
  2. Views created using thymeleaf.<br>
  3. The styles that we have used are placed<br>
 </p>
