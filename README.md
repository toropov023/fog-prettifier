# Fog Reports Prettifier
FOG is an inventory system used at Microcad. The reports generated by the system are not user-friendly so this GUI was created to truncate the CSV files leaving only the most important information and sending the formatted reports to specified emails.

The program is written with the help of JavaFX, [lombok](https://projectlombok.org) and [simple java mail](http://www.simplejavamail.org)

## Samples of the GUI at work:
1. The first screen the user sees when running the program:

![Start screen](https://i.imgur.com/7TsK3x8.png)

2. Once the CSV file is loaded, it is automatically processed by the program. Lenovo laptop models are not provided by the FOG system so the model names need to be looked up online by the serial. The program takes of that.

![Processed report file](https://i.imgur.com/r4uD2tJ.png)

3. The emails at the bottom are saved so when the program is opened again, it will populate those fields with previously used emails. If more emails are needed, extra fields are automatically added at the bottom.

![Extra email fields](https://i.imgur.com/FDHr74W.png)

4. The email is finally sent with the prettified CSV file attached and a summary table (similar to what is seen above) in the body of the email.
