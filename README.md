Est.
====

Est. stands for estimate. It's a Unix shell program for estimating cardinality of input and heavy hiters of the stream.
You definitely will find it useful if you are working with large amount of text data and have a need to estimate how many unique lines in a file or output of some program.

When do you need it?
====================
`cat file | sort | uniq | wc -l` – the simplest way to get the number of unique lines in a file. But there is one problem. Namely, `sort`. When you have tens of millions, and sometimes even millions of lines in a file, sorting become too expensive.
If you can trade precision for speed `est` utility is for you.

Installation
============
Java version 1.6 or higher is required for `est` utility.

Simply run fillowing command in your shell:

    curl -s https://raw.githubusercontent.com/bazhenov/est/master/install.sh | sh

`est` programm will be i nstalled in `/usr/local/bin`.

Usage
=====
To estimate the number of unique elements:

    $ cat file | est uniq
    3443

 To estimate top k most frequent elements in the stream

    $ cat file | est top
    3443

Performance
===========
On file containing 1 million of random numbers from 0 to 32756 `est` is about 30 times faster!

    time cat random.txt | sort | uniq | wc -l
    32768
    cat random.txt  0.00s user 0.01s system 0% cpu 14.340 total
    sort  15.57s user 0.12s system 99% cpu 15.782 total
    uniq  0.87s user 0.01s system 5% cpu 15.778 total
    wc -l  0.00s user 0.00s system 0% cpu 15.777 total

    time cat random.txt | est uniq
    32756
    cat random.txt  0.00s user 0.01s system 2% cpu 0.425 total
    est uniq  0.62s user 0.07s system 159% cpu 0.431 total