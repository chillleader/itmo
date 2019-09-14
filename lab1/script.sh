#!/bin/bash

##
## INITIALIZATION BLOCK
##
LOG_FILE=~/lab1_err

# get a carriage return into `cr` -- there *has* to be a better way to do this
CR=`echo $'\n.'`
CR=${CR%.}

##
## FUNCTIONS BLOCK
##
print_current_dir() {
    echo $PWD
    return $?
}

change_dir() {
    DIR=$1

    # given file does not exist
    if [ ! -e $DIR ]; then
        return 1
    fi

    # given file is not a directory
    if [ ! -d $DIR ]; then
        return 2
    fi

    # no execution permission
    if [ ! -x $DIR ]; then
        return 3
    fi

    cd $DIR 2>> $LOG_FILE
    return $?
}

print_dir_content() {
    DIR=$1

    # given file is not a directory
    if [ ! -d $DIR ]; then
        return 1
    fi

    # no read permission
    if [ ! -r $DIR ]; then
        return 2;
    fi

    # no execution permission
    if [ ! -x $DIR ]; then
        return 3;
    fi

    # list all files without '.' and '..'
    ls -1a $DIR 2>> $LOG_FILE | sed '/^[.]\{1,2\}$/d'

    return $?
}

create_direct_link() {
    SOURCE=$1; DEST=$2

    # given file does not exist
    if [ ! -e $SOURCE ]; then
        return 1
    fi

    # given file is a directory
    if [ -d $SOURCE ]; then
        return 2
    fi

    # no read permission for directory of source
    if [ ! -r `dirname $SOURCE` ]; then
        return 3
    fi

    # no execute permission for directory of source
    if [ ! -x `dirname $SOURCE` ]; then
        return 4
    fi

    # no write permission for directory of dest
    if [ ! -w `dirname $DEST` ]; then
        return 5
    fi

    # no execute permission for directory of dest
    if [ ! -w `dirname $DEST` ]; then
        return 6
    fi

    # create hard link
    ln $SOURCE $DEST 2>> $LOG_FILE
    return $?
}

remove_symlink() {
    LINK=$1

    for link in $LINK
    do
        # given file does not exist
        if [ ! -e $link ]; then
            return 1
        fi

        # file is not a symbolik link
        if [ ! -L $link ]; then
            return 2
        fi

        # no write permission
        if [ ! -w $link ]; then
            return 3
        fi

        # no execute permission
        if [ ! -x `dirname $link` ]; then
            return 4
        fi

        # show rm prompt, skip line
        echo "no" > ans
        rm -i $link < ans
        echo

        # and then repeat rm with user's answer
        read ANSWER
        if [ "$ANSWER" = "yes" -o "$ANSWER" = "y" ]; then
          rm $link
        fi
    done

    return $?
}

exit_program() {
    echo "Goodbye. Have a nice day!"
    return $?
}

print_help() {
    echo "[1] Print name of the current directory
[2] Switch the current directory
[3] Print content of the current directory
[4] Create a direct link
[5] Remove a symbolic link
[6] Exit the program" >&2
    return $?
}

##
## HANDLES BLOCK
##
handle_default() {
    CMD=$1; ERROR=$2
    # if error is not defined
    if [ -z ${ERROR} ]; then
      return;
    fi

    if [ $ERROR -ne 0 ]; then
        echo "Error $ERROR: Could not execute $CMD" >&2
    fi
}

handle_print_current_dir() {
    ERROR=$1; shift
    # if error is not defined
    if [ -z ${ERROR} ]; then
      return;
    fi

    if [ -z ${ERROR} ]; then
      case $ERROR in
          *)
              handle_default "print_current_dir" $ERROR
      esac
    fi

    handle_default "print_current_dir" $ERROR
}

handle_change_dir() {
    ERROR=$1; shift
    DIR=$1
    # if error is not defined
    if [ -z ${ERROR} ]; then
      return;
    fi

    if [ $ERROR -ne 0 ]; then
        case $ERROR in
            1)
                echo "Error: $DIR does not exist" >&2
                ;;
            2)
                echo "Error: $DIR is not a directory" >&2
                ;;
            3)
                echo "Erorr: No execution permission for $DIR" >&2
                ;;
            *)
                handle_default "change_dir" $ERROR
        esac
    else
        handle_default "change_dir" $ERROR
    fi
}

handle_print_dir_content() {
    ERROR=$1; shift
    DIR=$1
    # if error is not defined
    if [ -z ${ERROR} ]; then
      return;
    fi

    if [ $ERROR -ne 0 ]; then
        case $ERROR in
            2)
                echo "Error: No read permission for $DIR" >&2
                ;;
            3)
                echo "Error: No execution permission for $DIR" >&2
                ;;
            *)
                handle_default "print_dir_content" $ERROR
                ;;
        esac
    else
        handle_default "print_dir_content" $ERROR
    fi
}

handle_create_direct_link() {
    ERROR=$1; shift
    SOURCE=$1; DEST=$2
    echo $ERROR
    # if error is not defined
    if [ -z ${ERROR} ]; then
      return;
    fi

    if [ $ERROR -ne 0 ]; then
        case $ERROR in
            1)
                echo "Error: $SOURCE does not exist" >&2
                ;;
            2)
                echo "Error: $SOURCE is a directory" >&2
                ;;
            3)
                echo "Error: No read permission for `dirname $SOURCE`" >&2
                ;;
            4)
                echo "Error: No execute permission for `dirname $SOURCE`" >&2
                ;;
            5)
                echo "Error: No write permission for `dirname $DEST`" >&2
                ;;
            6)
                echo "Error: No execution permission for `dirname $DEST`" >&2
                ;;
            *)
                handle_default "create_direct_link" $ERROR
        esac
    else
        handle_default "create_direct_link" $ERROR
    fi
}

handle_remove_symlink() {
    ERROR=$1; shift
    LINK=$1
    # if error is not defined
    if [ -z ${ERROR} ]; then
      return;
    fi

    if [ $ERROR -ne 0 ]; then
        case $ERROR in
            1)
                echo "Error: $LINK does not exist" >&2
                ;;
            2)
                echo "Error: $LINK is not a symbolic link" >&2
                ;;
            3)
                echo "Error: No write permission for $LINK" >&2
                ;;
            3)
                echo "Error: No execute permission for `dirname $LINK`" >&2
                ;;
            *)
                handle_default "remove_symlink" $ERROR
        esac
    else
        handle_default "remove_symlink" $ERROR
    fi
}

handle_exit_program() {
    ERROR=$1; shift
    # if error is not defined
    if [ -z ${ERROR} ]; then
      return;
    fi

    if [ $ERROR -ne 0 ]; then
        case $ERROR in
            *)
                handle_default "exit_program" $ERROR
        esac
    else
        handle_default "exit_program" $ERROR
    fi
}

##
## MAIN BLOCK
##
while :
do
    print_help
    IFS=""; read -p "Enter a command: $CR" CMD >&2
    # if EOF or empty
    if [ -z ${CMD} ]; then
      exit_program
      break
    fi

    case $CMD in
        1)
            print_current_dir
            handle_print_current_dir $?
            ;;
        2)
            read -p "Enter path to the directory: $CR" DIR >&2
            change_dir $DIR
            handle_change_dir $? $DIR
            ;;
        3)
            print_dir_content $PWD
            handle_print_dir_content $? $PWD
            ;;
        4)
            read -p "Enter path to the source file: $CR" SOURCE >&2
            read -p "Enter path to the destitanion link: $CR" DEST >&2
            create_direct_link $SOURCE $DEST
            handle_create_direct_link $? $SOURCE $DEST
            ;;
        5)
            read -p "Enter path to link(s) to be deleted: $CR" LINK >&2
            for link in $LINK
            do
                remove_symlink $link
                handle_remove_symlink $? $link
            done
            ;;
        6|exit|quit|q)
            exit_program
            handle_exit_program $?
            break
            ;;
        help|h)
            print_help
            ;;
        *)
            echo "Unknown command: $CMD" >&2
            handle_default
    esac
done
