# MD5 (Hash algorithm!)

## What is MD5?
MD5 is a hashing algorithm! What is a hashing algorithm? According to the Federal Agencies Guidlines Initiative, a hashing algorithm is a function that converts a data string into a numeric string output (which might look like: d41d8cd98f00b204e9800998ecf8427e). 

### Why do we need hashing algorithms? Why are they useful?
Data is put through hash algorithms for its protection (ensures data integrity)! The idea is if someone gains access to your server, they would be unable to read the data even though the data is technically stored there. 

### But how does Hashing achieve this? 
Let's say you're logging on to your email. After you type in your email you (hopefully) need to type in your password.
![Logging in example](https://github.com/Stuycs-K/final-project-09-gao-rachel-ciu-nathaniel/blob/main/pictures/Logging%20in.png)

Now google or whatever service you use for emails has to verify that you have typed the right password. That's should be simple, right? They'll just compare what you typed in with the password they have saved on their website/servers! __**Nope!**__. Google and any reasonable service that has a login process do not save users passwords directly on their website. 

### Well, why don't services save passwords directly?
Saving passwords makes it easier for any malicious entity to hackjack user accounts. If black hat hackers (the bad guys) happen to break into a database and finds account names and passwords imagine the havoc they can produce! But we still need to verify whether or not you have typed the right password. This an instance where hash alogrithims comes in. You see, instead of saving your password directly, the service saves your password's hash. So everytime you want to login to your account instead of comparing the password you typed in against the password they have in their database, they take the password you typed in  and put it through a hash algorithim. Then they compare the hash produced with the hash they have saved. If the two match, you are granted access into your account!
