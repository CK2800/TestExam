# jwtBackend

Start code for exercises, CA-3 and the Semester Project given at cphbusiness.dk - computer science

<B>To start from this project:</B><br />
Switch to the cloneReady branch after cloning <br />
OR<br/>
Clone the branch directly: git clone -b cloneReady --single-branch https://github.com/CK2800/CA3.git<br /><br />
<B>REMEMBER:</B>
1. To rename the folder after cloning, since the default name will be CA3.
2. To remove the .git folder and run git init to set up the correct remotes.<br /><br />

<B>The project contains two parts:</B><br />
1. A backend part written in Java containing a REST endpoint.<br /> 
2. A frontend part written in React. This part is found in the CA3/react-ui folder.

<B>Databases</B><br />
You must create two database schemas, <B>a) one for local development</B> and <B>b) one for local integration testing</B>.
<br /><br />
Edit the two corresponding persistence units:<br />
* XXpu.properties must contain:<br />
a: the name of your database schema used for development. (a)<br />
b: your user name and password for the MySql server.
<br /><br />
* XXpu_integration_test.properties must contain:<br />
a: the name of your database schema used for local integration tests. (b)<br />
b: your user name and password for the MySql server.
<br /><br />
* Rename the two files - remove XX from file names.
<br><br>
* To set time zone on MySql server if needed -> sql: SET global time_zone = '+1:00';
<br /><br />

<B>To use the react-ui, you must open the folder in VS code.</B><br>
1. If the project does not contain a 'Node_modules' folder, write in cmd:  npm install and npm install react-router-dom<br />
2. To start the project, write in cmd: npm start<br />
3. Make your changes to the project, to build it, write in cmd: npm run build<br />
4. To install surge, write in cmd: npm install --global surge<br />
5. After build, to host on surge, write in cmd: surge --project ./build --domain A_DOMAIN_NAME.surge.sh and fill out form.<br />
<br /><hr />
Additional info can be found here: https://docs.google.com/document/d/1GZvVK-dGHjyP30BULhtRz9EFYd9MOzdtChoV6X_mRnQ/edit
