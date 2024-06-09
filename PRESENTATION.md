# MD5 (Hash algorithm!)

## What is MD5?
MD5 is a hashing algorithm! What is a hashing algorithm? According to the Federal Agencies Guidelines Initiative, a hashing algorithm is a function that converts a data string into a numeric string output (which might look like: d41d8cd98f00b204e9800998ecf8427e). 

### Why do we need hashing algorithms? Why are they useful?
Data is put through hash algorithms for its protection (ensures data integrity)! The idea is if someone gains access to your server, they would be unable to read the data even though the data is technically stored there. 

![When hackers try to read a hashed database](https://github.com/Stuycs-K/final-project-09-gao-rachel-ciu-nathaniel/blob/main/pictures/Finding%20a%20hash%20database.png)

### But how does Hashing achieve this? 
Let's say you're logging on to your email. After you type in your email you (hopefully) need to type in your password.
![Logging in example](https://github.com/Stuycs-K/final-project-09-gao-rachel-ciu-nathaniel/blob/main/pictures/Logging%20in.png)

Now google or whatever service you use for emails has to verify that you have typed the right password. That's should be simple, right? They'll just compare what you typed in with the password they have saved on their website/servers! __**Nope!**__. Google and any reasonable service that has a login process do not save users passwords directly on their website. 

### Well, why don't services save passwords directly?
Saving passwords makes it easier for any malicious entity to hackjack user accounts. If black hat hackers (the bad guys) happen to break into a database and finds account names and passwords imagine the havoc they can produce! But we still need to verify whether or not you have typed the right password. This an instance where hash alogrithims comes in. You see, instead of saving your password directly, the service saves your password's hash. So everytime you want to login to your account instead of comparing the password you typed in against the password they have in their database, they take the password you typed in and put it through a hash algorithim. Then they compare the hash produced with the hash they have saved. If the two match, you are granted access into your account!
![Login Services be like](https://github.com/Stuycs-K/final-project-09-gao-rachel-ciu-nathaniel/blob/main/pictures/It's%20the%20same%20thing.png)

### What prevents the bad guys from reversing the hash to get the password?
Hash algorithims are designed to be one way functions, so if you happen to get an output (a hash), it would be very difficult to reverse the algorithim determine the input (the password or original data). 

![Hackers when they find a hash database](https://github.com/Stuycs-K/final-project-09-gao-rachel-ciu-nathaniel/blob/main/pictures/Ah%20No%20Hashes%202.png)


### So is MD5 used for storing "passwords"?
Nope, MD5 shouldn't be used for hashing passwords because of how quickly a malicious entity can brute force it with today's technology! MD5 has also be proven to cause collisions! This means that two different inputs could produce the same output, which is a fatal flaw for password checking (or any security: e.x. [checksums](https://www.techtarget.com/searchsecurity/definition/checksum#How%20to%20check%20a%20MD5%20checksum) that are used to prevent tampering).  
![Collision Attacks be like](https://github.com/Stuycs-K/final-project-09-gao-rachel-ciu-nathaniel/blob/main/pictures/Collision%20Attacks%20be%20like.png) 
![Hackers Delight](https://github.com/Stuycs-K/final-project-09-gao-rachel-ciu-nathaniel/blob/main/pictures/But%20It's%20MD5.png)

MD5 is still used to authenicate messages and files sent from one device to another (but messages and files are still vulnerable to [collision attacks](https://www.gomyitguy.com/blog-news-updates/hash-collision-attacks)) 

## Brief History of MD5
Invented by Ronald Rivest, MD5 was released in 1992 to replace MD4 (which was deemed insecure since the beginning of the 1990's, due to collision attacks). MD5 had more complex hash calculations than MD4 (and was also slower due to these complex hash calculations).

But MD5 suffered from similar collision problems. In 1996, research was published describing a weakness in the MD5 algorithim (most likely the short hash length) that made it vulnerable to collisions. In 2004, [more research](https://eprint.iacr.org/2004/199.pdf) was published on the practically of a malicious entity to generate collisions. In 2005, there was a demonstration that generated colliding [x.509 certificates](https://www.ssl.com/faqs/what-is-an-x-509-certificate/). In 2008, Carnegie Mellon University declared that MD5 should be "considered cryptographically broken" due to its vulunerablility to collision attacks. In 2009 at the 25th annual Chaos Communication Conference (25C3), [presenters claimed that they used 200 PlayStation 3 game consoles to attack a single hash in four weekends](https://www.speedguide.net/news/md5-is-officially-insecure-hackers-break-ssl-certificates-2752) costing them $657, which was impressive at the time.

[MD5 is still used today](https://www.zdnet.com/article/a-quarter-of-major-cmss-use-outdated-md5-as-the-default-password-hashing-scheme/), dispites its drawbacks and better alternatives. Some speculations (note these speculations were taken from at least 10 year old forums) of why MD5 is still used is that developers don't know any better, developers are ignorant about better solutions, MD5 was what was traditionally used and old habits die hard (also legacy systems), the vunlerabilities aren't significant enough to be an obvious problem, compatibility with software, performance speed. Generally using MD5 today is not advisable. 

### What are collision attacks?
Because MD5's main weakness is collisions attacks, let's 

## How MD5 Works

