# project-1
------------------
FINISHED
------------------
<b>CSCI 446 — Artificial Intelligence<br>
Project #1<br>
Search, Constraint Satisfaction, and Graph Coloring</b><br>
<br>
The purpose of this assignment is to give you an introduction to artificial intelligence from the perspective<br>
of search. Being that this is a team project, several elements will need to be implemented. This project is a<br>
variation and extension of the problem in the textbook (Russell & Norvig, 6.10).<br>
For this project, you need to write a “problem generator” whereby you will create several graphs of various<br>
sizes to solve instances of the graph-coloring problem. For your graph generator, scatter n points on the unit<br>
square (where n will be provided as input), select some point X at random and connect X by a straight line<br>
to the nearest point Y such that X is not already connected to Y and line crosses no other line. Repeat the<br>
previous step until no more connections are possible. The points represent regions on the map, and the lines<br>
connect neighbors.<br>
  After building the graph, try to find k-colorings of each map for both k = 3 and k = 4 using min-conflicts,<br>
backtracking, backtracking with forward checking, and backtracking with MAC. You will also attempt to<br>
find such colorings using a genetic algorithm with tournament selection and a fitness function with a penalty<br>
term.<br>
<br>
<b>Here are the specific steps that need to be followed:</b><br>
• Implement the graph generator.<br><br>
• Generate at least 10 different graphs of sizes ranging from 10 to 100 vertices in steps of 10 (i.e., 10, 20,<br>
30, 40, 50, 60, 70, 80, 90, 100).<br><br>
• Implement a constraint solver to find 3-colorings and 4-colorings for each graph (if they exist), implementing<br>
the following variations:<br>
– Min conflicts<br>
– Simple backtracking<br>
– Backtracking with forward checking<br>
– Backtracking with constraint propagation (MAC)<br>
– Local search using a genetic algorithm with a repair function and tournament selection<br><br>
• Write a design document that outlines the architecture of your software (a UML class diagram is encouraged<br>
but not required), design decisions made in implementing your algorithms, and the experimental<br>
design for testing the results.<br><br>
• Run experiments, keeping track of the main decisions being made for each algorithm. The count of<br>
these decisions will be used to gauge run time since CPU time and wall clock time are not reliable<br>
estimators.<br><br>
• Write a paper that incorporates the following elements, summarizing the results of your experiments.<br>
Make sure you explain the experimental setup, the tuning process, and the final parameters used for<br>
each algorithm.<br>
1. Title and author names<br>
2. A brief, one paragraph abstract summarizing the results of the experiments<br>
3. Problem statement, including hypothesis (how do you expect the algorithms to do?)<br>
4. Description of algorithms implemented<br>
5. Description of your experimental approach<br>
6. Presentation of the results of your experiments<br>
7. A discussion of the behavior of your algorithms, combined with any conclusions you can draw<br>
8. Summary<br>
9. References (you should have at least one reference related to each of the algorithms implemented,<br>
a reference to the data sources, and any other references you consider to be relevant)<br><br>
• Submit your fully documented code for the data converter, results of the runs of each algorithm, your<br>
design document, and your paper.<br>
<br><br>
<b>Due Dates:</b><br>
• Design Document – September 16, 2016<br>
• Program Code and Sample Runs – September 23, 2016<br>
• Project Report – September 26, 2016<br>
