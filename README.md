# Episode 01: Outbreak!

## Using CLI Tools
CLI stands for Command Line Interface. These are tools created in Java that you can use from your terminal.

The `$` symbol indicates a command you can type in your terminal. You don't have to include the `$`.

To use Java programs, you must compile (`javac`) and run (`java`) them. It usually looks like this:
```
javac ProgramName.java
java ProgramName
```

## CLI Tools Included

### CaseReader

Converts encoded data about people who have gotten sick into a human-readable format.

#### Instructions
```
$ javac tools/*java
$ java tools.CaseReader filename month day
```
- filename: which file to decode
- month: name of month when case monitoring began
- day: day number when case monitoring began

#### Output
```
$ java tools.CaseReader data/filename.txt April 1

Case ID: LT0295
Started feeling sick on April 9 around 8:00 AM
Age: 30
Home: Location 197
Workplace: Restaurant 13 (R)
Outing History:
- Had lunch at Restaurant 1 (R) on April 1
- Had lunch at Restaurant 6 (R) on April 2
- Had lunch at Restaurant 10 (R) on April 3
- Had lunch at Restaurant 10 (R) on April 4
- Had lunch at Restaurant 7 (R) on April 5
- Had lunch at Restaurant 8 (R) on April 6
- Had lunch at Restaurant 18 (R) on April 7
- Had lunch at Restaurant 19 (R) on April 8
```

### AlphaModel

Makes predictions about an outbreak based on given parameters.

#### Instructions
```
$ javac tools/AlphaModel.java
$ java tools.AlphaModel total infected beta gamma
```
- total: total number of people in the city
- infected: percentage of population infected
- beta: probability of becoming infected
- gamma: probability of becoming healthy

#### Output
```
$ java tools.AlphaModel 1000 0.05 0.3 0.2

Outbreak Lasted 86 days.
At peak, 98 people were infected.
In total, 631 people were infected.
```

### BetaModel

Makes predictions about an outbreak based on given parameters.

#### Instructions
```
$ javac tools/BetaModel.java
$ java tools.BetaModel total infected encounters transmission contagion recovery
```
- total: total number of people in the city
- infected: percentage of population infected
- encounters: number of healthy people an infected person comes in contact with
- transmission: probability of an encounter leading to exposure
- contagion: number of days between exposure and sickness
- recovery: number of days between exposure and recovery

#### Output
```
$ java tools.BetaModel 1000 0.05 0.3 10 1 5

Outbreak Lasted 25 days.
At peak, 727 people were infected.
In total, 999 people were infected.
```