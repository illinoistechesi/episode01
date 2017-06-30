# Episode 01: Outbreak!


## CLI Tools
CLI stands for Command Line Interface. These are tools created in Java that you can use from your terminal.

### CaseReader
The `$` symbol indicates that this is a command you can type in your terminal. You don't have to include the `$`. Below are basic instructions to compile (`javac`) and run (`java`) the CaseReader tool, which converts encoded data about people who have gotten sick into a human-readable format.
```
$ javac tools/*java
$ java tools.CaseReader data/filename.txt

```
In the above command `tools.CaseReader` tells the computer to run the CaseReader program in the tools folder. The part after that tells it which file to decode.

The CaseReader uses January 1st as the default start date for cases. You can update it to the correct date for your outbreak like this:
```
$ java tools.CaseReader data/filename.txt April 3

```
The results will show up in your terminal like this:
```
Case #295
Started feeling sick on January 9 around 8:00 AM
Age: 30
Home: Location 197
Workplace: Restaurant 13 (R)
Outing History:
- Had lunch at Restaurant 1 (R) on January 1
- Had lunch at Restaurant 6 (R) on January 2
- Had lunch at Restaurant 10 (R) on January 3
- Had lunch at Restaurant 10 (R) on January 4
- Had lunch at Restaurant 7 (R) on January 5
- Had lunch at Restaurant 8 (R) on January 6
- Had lunch at Restaurant 18 (R) on January 7
- Had lunch at Restaurant 19 (R) on January 8
```