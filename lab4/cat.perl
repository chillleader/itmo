#!/usr/bin/perl -w

use strict;
use warnings;
use Getopt::Long;
use File::Basename;

my $index = 0;
my $linenum = 1;
my $script = basename $0;
my ($all, $num_nonblank, $number, $tab, $end, $help);
my ($squeeze_blank, $blank);

my $Usage = "
Usage: $script [option]... [file]...
Concatenate FILE(s), or standard input, to standard output.
  -A, --show-all           equivalent to -vET
  -b, --number-nonblank    number nonempty output lines, overrides -n
  -e                       equivalent to -vE
  -E, --show-ends          display \$ at end of each line
  -n, --number             number all output lines
  -s, --squeeze-blank      suppress repeated empty output lines
  -t                       equivalent to -vT
  -T, --show-tabs          display TAB characters as ^I
  -u                       (ignored)
  -v, --show-nonprinting   use ^ and M- notation, except for LFD and TAB
      --help     display this help and exit
With no FILE, or when FILE is -, read standard input.
";

GetOptions(
    'show-all|A'        => \$all,
    'number-nonblank|b' => \$num_nonblank,
    'number|n!'         => \$number, #the '!' means can use --[no]number disable -n option
    'squeeze-blank|s'   => \$squeeze_blank,
    'show-ends|E'       => \$end,
    'show-tabs|T'       => \$tab,
    'help|h'            => \$help
);

my @files = @ARGV;

if ($help) {
    &print_help();
}

if ($all) {
    $end = 1;
    $tab = 1;
}

if ($num_nonblank) {
    $number = 1;
}
$blank = 0;

while (my $line = <>) {
    if ($line =~ /^\s*$/) {
        if ($squeeze_blank) {
            if ($blank) {
                next;
            }
        }

        if ($tab) {
            $line =~ s/\t/\^I/sg;
        }

        if ($end) {
            $line =~ s/(\n|\n\r)/\$$1/;
        }

        if ($num_nonblank) {
            print "$line";
            $blank = 1;
            next;
        }

        if ($number) {
            printf("%6d  ", $linenum++);
        }

        print "$line";
        $blank = 1;
    }
    else {
        if ($number) {
            printf("%6d  ", $linenum++);
        }

        if ($tab) {
            $line =~ s/\t/\^I/sg;
        }

        if ($end) {
            $line =~ s/(\n|\n\r)/\$$1/;
        }

        print $line;
        $blank = 0;
    }

    if (eof) {
        print("--------end of $files[$index] file--------\n");
        $index += 1;
    }
}

sub print_help {
    print $Usage;
    exit;
}

### @files
### $index
### $tab
### @ARGV
### $number
### $help
### $debug