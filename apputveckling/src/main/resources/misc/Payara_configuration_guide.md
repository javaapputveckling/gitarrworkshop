# Configuration guide for the database connection with Payara and MySQL(via XAMPP)

1. Download MySQL Connector/J (platform independent version) and unzip the folder to
   /PAYARADIR/glassfish/domains/domain1/
   *Replace PAYARADIR with the path of your payara6 folder

2. Open a terminal/command prompt in the location /PAYARADIR/bin and run the following command: ./asadmin add-library
   /PAYARADIR/glassfish/domains/domain1/lib/mysql-connector-j-8.2.0/mysql-connector-j-8.2.0.jar

   Now open a new terminal in /PAYARADIR/glassfish/bin and run the same command as above.
   *Replace PAYARADIR with the path of your payara6 folder

3. Follow this guide to procceed with the MySQL configuration in Payara's GUI:
   https://blog.payara.fish/using-mysql-with-payara