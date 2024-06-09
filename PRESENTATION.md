# MD5 (Hash algorithm!)

## What is MD5?
MD5 is a hashing algorithm! What is a hashing algorithm? According to the Federal Agencies Guidelines Initiative, a hashing algorithm is a function that converts a data string into a numeric string output (which might look like: d41d8cd98f00b204e9800998ecf8427e). 

### Why do we need hashing algorithms? Why are they useful?
Data is put through hash algorithms for its protection (ensures data integrity)! The idea is if someone gains access to your server, they would be unable to read the data even though the data is technically stored there. 

![When hackers try to read a hashed database](https://github.com/Stuycs-K/final-project-09-gao-rachel-ciu-nathaniel/blob/main/pictures/Finding%20a%20hash%20database%202.png)

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

[MD5 is still used today](https://thehackernews.com/2022/11/french-electricity-provider-fined-for.html) ([another less recent example](https://www.zdnet.com/article/a-quarter-of-major-cmss-use-outdated-md5-as-the-default-password-hashing-scheme/)), dispites its drawbacks and better alternatives. Some speculations (note these speculations were taken from at least 10 year old forums) of why MD5 is still used is that developers are ignorant about its problems, developers are ignorant about better solutions, legacy systems / compatibility with software, and performance speed. Generally using MD5 today is not advisable. 

![Some devs when they find out they shouldn't use MD5](https://github.com/Stuycs-K/final-project-09-gao-rachel-ciu-nathaniel/blob/main/pictures/But%20I%20like%20it%20sad%20face.png)
![Switch from MD5 or draw 25](https://github.com/Stuycs-K/final-project-09-gao-rachel-ciu-nathaniel/blob/main/pictures/Uno%20Game.png)

## Collisions!
Because MD5's main weakness is collisions attacks, let's dive into collisions in greater detail! As stated before, hash collisions happens when two inputs generate the same hash value. The reason this is a problem is because malicious entities to undermine authentication and integrity of digital signatures (basically another name for hashes) because they can pretend to be an authorized user or have tampered files be marked as legitimate. But it's actually impossible to avoid hash collisions.

![One does not simply avoid hash collisions](https://github.com/Stuycs-K/final-project-09-gao-rachel-ciu-nathaniel/blob/main/pictures/Avoid%20Collisions.png)

### Why are collision unavoidable?
The pigeonhole principle basically goes like this if there are more inputs than outputs, then some inputs must share the same output (Called the pigeonhole principle because of this explaination: If there are more pigeons than pigeonholes, some of the holes must contain more than one pigeon). 

![Too many pigeons for the holes](https://github.com/Stuycs-K/final-project-09-gao-rachel-ciu-nathaniel/blob/main/pictures/Pigeon%20Hole.png)

Because MD5 hashes are always a set length (128 bits) and an infinite amount of inputs there will be some inputs that share the same output. This is also true for SHA-256 (another hash algorithim), which has a set length of 256 bits. **But SHA-256 is considered secure wihle MD5 is not. Why?**

The reason why SHA-256 is considered secure is because it's not really fleasible (for now) to find two different inputs that result in the same output. Why? Well because SHA-256 produces longer hashes there are way more combinations available so finding a collision with today's technology would take too much time, effort, and resources. This used to be the case for MD5 as well! Even though MD5 was theorically collision vulnerable, the technology of the 1990's could not practically find two inputs that shared the same output within a reasonable time frame and resource investment, which was why people still used it after the 1996 research paper. 
![But it's not possible](https://github.com/Stuycs-K/final-project-09-gao-rachel-ciu-nathaniel/blob/main/pictures/But%20it%20is%20not%20possible.png)

## How MD5 Works
When MD5 recieves the input the first thing it does is pad it! 10000000 is first added to the binary of the input and then 00000000 is added in order to ensure that the input is divisble by 512. The reason being is that MD5 works with 512 bit blocks! We also need to have at least one byte of padding so if the input is exactly 512 bits (or a multiple of 512) we need another 512 block. When the padding is done the original input length is stored using the last eight bytes in the last block. 
![MD5 Padding Input](https://github.com/Stuycs-K/final-project-09-gao-rachel-ciu-nathaniel/blob/main/pictures/MD5%20Padding%20Input.png)

Once we're done padding and have nice 512 bit blocks, we need to split our blocks into 4 byte "words". Because each block is 512 bits (or 64 bytes) we should have 16 words for each block! 

Before we move on with manipulating said words we need to establish a few constants! 

### Constants
**All values given in little endian**

There are four initials values. Let's call then A,B,C, and D!
```
A = 0x67452301 
B = 0x67452301  
C = 0x98badcfe
D = 0x10325476
```


There are 4 rounds in the MD5 algorithim. In these rounds we will use different constants for different things.

First we have the order in which we have to add the words to the initial value A.
```
Round 0: the words in numerial order (0, 1, 2, ..., 15) 
Round 1: 1, 6, 11, 0, 5, 10, 15, 4, 9, 14, 3, 8, 13, 2, 7, 12
Round 2: 5, 8, 11, 14, 1, 4, 7, 10, 13, 0, 3, 6, 9, 12, 15, 2
Round 3: 0, 7, 14, 5, 12, 3, 10, 1, 8, 15, 6, 13, 4, 11, 2, 9
```


We have a constant to add to the word and the initial value. The constant, let's call it K, can be calculated using
```
(1L << 32) * Math.abs(Math.sin(i + 1)) with i denoting the K value. Ex.) if i = 0, then Ki = K0 = 0xd76aa478
or in more math terms
the absolute value of (sin(i + 1)) * 2 to the 32nd power)
```


We also have a specific amount to bit shift a word by. 
```
Round 0: 7, 12, 17, 22,  7, 12, 17, 22,  7, 12, 17, 22,  7, 12, 17, 22,
Round 1: 5,  9, 14, 20,  5,  9, 14, 20,  5,  9, 14, 20,  5,  9, 14, 20,
Round 2: 4, 11, 16, 23,  4, 11, 16, 23,  4, 11, 16, 23,  4, 11, 16, 23,
Round 3: 6, 10, 15, 21,  6, 10, 15, 21,  6, 10, 15, 21,  6, 10, 15, 21
```
Notice that each round consists of four repeating numbers. (These numbers repeat because it's just easier to iterate over) 


Then we we have the bitwise operations for each round
```
Round 0: (B & C) | (~B & D)
Round 1: (B & D) | (C & ~D)
Round 2: (B ^ C ^ D)
Round 3 : C⊕(B∨¬D)
```
Yes those letters are indeed referencing the initial values we have earlier!

Got all that?

![Studying](https://i.giphy.com/media/v1.Y2lkPTc5MGI3NjExbW95bGtrbjBhbmJpamphOTQ1NHV5ajkwbmJoc2g3dzMyNnUxODBmMiZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/KCqO4k31TnkC2pT5LY/giphy-downsized-large.gif)


Let's actually start getting into the main algorithim!

### Main algorithim
For the first round

Take the value of the Round 0 bitwise operation and add it to the initial value A and modulo that result with 0x100000000L!

Then add that result with the the correct word! In this case (round 0, step 0), we need to add word #0 with the result of the previous expression! Modulo this result with 0x100000000L. And after that modulo, mudulo (yes again) that result with 0xFFFFFFFFL.

Then add the correct K value! In this case (round 0, step 0), we need to add K0 to the previous result! Also modulo this result with 0x100000000L. And after that modulo, modulo (yes again) that result with 0xFFFFFFFFL.

After that we need to shift the bits of our result (and modulo it)!
```
(result << correctShift) | (FAMK >> (32 - correctShift) )  % 0x100000000L
```
In this case (round 0, step 0), we need to shift by 7!

After that add B to the result of the previous expression! 

Then re-assign the values as follows
```
A = D
D = C
C = B
B = the value we just calculated!
```

Repeat the first round 15 more times, exchanging constants as needed! 

After that move on the the 2nd, 3rd, and 4th round!
The only change that happens is the bitwise operation! The constants should be easily iterated through!



