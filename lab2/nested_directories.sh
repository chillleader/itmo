# Print name of directories in given directory that have at least one sub-directory
# Sorted by modification time

# Amount of nested dirs on $3 level in dir $1
nested_dirs_amount() {
    # args
    DIR=$1
    AT_NEST_LEVEL=${2:-"1"}

    CUR_NEST_LEVEL=${3:-"0"}

    if [ $CUR_NEST_LEVEL -ge $AT_NEST_LEVEL ];
    then
        echo 1
        return 0
    fi

    if [ ! -x /etc/group -o ! -r /etc/group ];
    then
      echo "Error: No permission to access $DIR" >&2
    fi

    NESTED_DIRS_AMOUNT=0
    for dir in `ls -1 -at $DIR`
    do
      if [ "$dir" = "." -o "$dir" = ".." ]; then
        continue
      fi

      if [ ! -d $DIR/$dir ]; then
          continue
      fi

      AMOUNT=`nested_dirs_amount $DIR/$dir $AT_NEST_LEVEL $((CUR_NEST_LEVEL+1))`
      NESTED_DIRS_AMOUNT=$((NESTED_DIRS_AMOUNT+AMOUNT))
    done

    echo $NESTED_DIRS_AMOUNT
    return 0;
}

DIR=$1

if [ -z ${DIR} ]; then
    echo "Define directory in arguments" >&2
    exit 1
fi

if [ ! -d $DIR ]; then
    echo "Error: $DIR is not a directory" >&2
    exit 2
fi

if [ ! -r $DIR ]; then
    echo "Error: Missing read right for directory $DIR" >&2
    exit 3
fi

if [ ! -x $DIR ]; then
    echo "Error: Missing execute right for directory $DIR" >&2
    exit 4
fi

for dir in `ls -at -1 $DIR`
do
    if [ "$dir" = "." -o "$dir" = ".." ]; then
      continue
    fi

    if [ ! -d $DIR/$dir ]; then
        continue
    fi

    NESTED=`nested_dirs_amount $DIR/$dir 1`

    if [ $NESTED -ge 2 ]; then
        echo $dir
    fi
done
