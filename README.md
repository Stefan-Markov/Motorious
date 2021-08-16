# Motorious
Deployed with Heroku, changed database from MySQL to PostreSQL,little changes to thymeleaf, etc.

Application in general:
- 11 controllers
- 9 services
- 6 independent entities
- 6 repositories
- models - binding, view, service
- Spring Security (prevent SQL injection, CSRF, parameter tampering, secure routes,secure password) 
- Thymeleaf Security
- Thymeleaf
- AJAX
- Fetch Api 
- Async, await
- Rest
- HTML/CSS
- JavaScript
- Spring Events
- Spring AOP
- Mapping library - Model mapper
- Data validation - frontend and backend
- Custom validator
- Using layers with multiple layouts 
- Testing - unit test and integration tests for some of the controllers using mocking - 80% coverage
- Exceptions handling and CustomAccessDeniedHandler
- Custom error pages
- Interceptors
- Spring Cache
- Spring Schedule tasks
- MySQL database
- JPA
- Cloudinary API for picture upload
- Google API for data visualisation
- Google API for maps
- Media queries for responsive design

Application functionalities:
- 4 manageable roles - ROOT, ADMIN, KINESITHERAPIST, USER
- Picture upload if user want via Cloudinary API
- Search engine(find user by username or email) and generate dynamic content - AJAX with JPA filtration
- Search treatment by keyword - generate dynamic content with AJAX.
- Add, delete users
- Registration, login
- Add, delete treatment
- Add, delete measurement
- Add, delete blog
- View all users
- View all clients by kinesitherapist
- View all blogs
- Contact form
- Update user details
- View yours treatments
- View yours measurements
- Downgrade role of user 
- Change role of user
- Information page
- Measurement activity of action - Spring AOP
- Delete user or register user - Spring Events
- When user try to access unauthorised route, log the information in app
- Added address on the maps for navigation via Google API 
- Responsive design for smartphones, tablets, laptop, etc.
- JavaScript calendar 

To try the app, please enter environment variables on your IDE for 
- MySQL username and password
- Cloudinary credentials
