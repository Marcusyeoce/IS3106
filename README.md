# IS3106 Project - Plandr

Group 15 Members:

Celesse Tay Yi Qi, A0187804W, celesse@u.nus.edu, 91883968

Wong Oi Mun, A0189396E, oimun@u.nus.edu, 91031316

Yeo Chong Ern, Marcus, A0183414J

Pham The Dung, A0184613E

Database:
- JNDI Name: jdbc/plandrsystem
- JDBC Connection Pool Name: plandrsystemconnectionPool
- Data Source Class Name: com.mysql.cj.jdbc.MysqlDataSource
- Database Name: PlandrSystem
- Port: 3306
- Suffix: ?zeroDateTimeBehavior=convertToNull
- User: root
- Password: password

EJB Persistence Unit: PlandrSystem-ejbPU

Structure:
Enterprise Application - PlandrSystem
	EJB Module - PlandrSystem-ejb
	Web Application Module (Management) - PlandrManagementSystem
	RESTful Web Services - PlandrSystemRws

Angular Client System - PlandrClient

(*Notes: PlandrSystem-war is deprecated)

Deploying:
- Create the necessary database, EJB PU, etc.
- Open PlandrSystem and the required projects mentioned above.
- NetBeans will prompt missing black-tie-1.0.10.jar. The file can be located in "src\Related Jar Files".
- Resolve any referencing issues.
- NetBeans may prompt deployment descriptor directory not found because the WEB-INF folder is missing for PlandrSystemRws. You can either:
  + Create an empty WEB-INF folder in "\src\Plandr\PlandrSystem\PlandrSystemRws\web".
  + Ignore the warning.

- Access "\Plandr\PlandrSystem\PlandrManagementSystem\web\WEB-INF\glassfish-web.xml" file. On line 15 you should see this line: 
<property name="alternatedocroot_1" value="from=/uploadedFiles/* (Some directory)\uploadedFiles"/>
- Change "(Some directory)\Plandr\uploadedFiles" to the directory for "\src\uploadedFiles" folder.
- Clean and Build the entire prototype.
- Deploy PlandrSystem.

Running PlandrManagementSystem: 
- Navigate to https://localhost:8080/PlandrManagementSystem 
- For ADMIN use cases, login with the following credentials: admin, password
- For EMPLOYEE use cases, login with the following credentials: employee, password

Running PlandrClient:
- Open Command Prompt for "\src\Plandr\PlandrSystem\PlandrClient"
- Run the following commands:
npm install
ng serve

- Navigate to https://localhost:4200
- Login using the following credentials: member1, password || member2, password









