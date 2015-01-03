Est.
====

Est. stands for estimate. It's a Unix shell script for estimating cardinality of input.
You definitely will find it useful if you are working with large amount of text data and have a need to estimate how many unique lines in a file or output of some program.

When do you need it?
=================
`cat file | sort | uniq | wc -l` – the simplest way to get the number of unique lines in a file. But there is one problem. Namely, `sort`. When you have tens of millions, and sometimes even millions of lines in a file, sorting become too expensive.
