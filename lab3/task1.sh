#!/bin/bash

# Print list of users, who have read right for given file

LOG_FILE=~/lab3_log
exec 2>> $LOG_FILE
echo Run at `date` 1>&2

# Get ACL rights entry for user
# args: file, uid
# output: rights set in format [r\-][w\-][x\-] (example: r-x)
get_acl_rights_entry() {
  FILE=$1
  USER_ID=$2

  # get acl entries
  ENTRIES=`getfacl -acn $FILE | head -n -1`
  if [ $? -ne 0 ]; then
    return 2;
  fi

  # get user's groups
  USER_GROUPS=`id -G $USER_ID`
  if [ $? -ne 0 ]; then
    return 3;
  fi

  STAT=`stat -c '%u %g' $FILE`
  OWNER_UID=`echo $STAT | cut -f1 -d' '`
  OWNER_GID=`echo $STAT | cut -f2 -d' '`

  echo -n $ENTRIES | awk \
  -F:\
  -v RS=" "\
  -v user_uid="$USER_ID"\
  -v user_gids="$USER_GROUPS"\
  -v owner_uid="$OWNER_UID"\
  -v owner_gid="$OWNER_GID"\
  '
  function is_in(element, array) {
    for(elem in array) {
      if(element == elem) {
        return true;
      }
    }
    return false;
  }

  function map_rights(letters) {
    return 4 * (substr(letters, 1, 1) == "r" ? 1 : 0) + 2 * (substr(letters, 2, 1) == "w" ? 1 : 0) + 1 * (substr(letters, 3, 1) == "x" ? 1 : 0)
  }

  BEGIN {
    split(user_gids, user_groups, " ");
    other = -1;
    mask = -1;
  }

  {
    type = $1;
    id = strtonum($2 == "" ? "-1" : $2);
    rights=map_rights($3)

    if(type == "user") {
      user[id] = rights;
    } else if(type == "group") {
      groups[id] = rights;
    } else if (type == "other") {
      other = rights;
    } else if (type == "mask") {
      mask = rights;
    }
  }

  END {
    if (user_uid == owner_uid) {
      entry[1] = user[-1];
    }

    if (user[user_uid] != "") {
      entry[2] = user[user_uid];
    }

    if (is_in(owner_gid, user_groups)) {
      entry[3] = groups[-1];
    }

    met = false;
    for(group in user_groups) {
      rights = 0;
      if(groups[user_groups[group]] != "") {
        met = true;
        rights = or(rights, groups[user_groups[group]])
      }
    }

    if(met) {
      entry[4] = rights;
    }

    entry[5] = other;

    rights = 0;
    for(e in entry) {
      rights = or(rights, entry[e]);
    }

    if(mask != -1) {
      rights = and(rights, mask);
    }

    print rights;
    }
  '
}

# Get simple rights entry for user
# args: file, uid
# output: octal number with user's rights for file
get_rights_entry() {
  FILE=$1
  USER_ID=$2

  # find out file rights, owner uid, owner gid
  STAT=`stat -c '%a %u %g' $FILE`
  RIGHTS=`echo $STAT | cut -f1 -d' '`
  OWNER_UID=`echo $STAT | cut -f2 -d' '`
  OWNER_GID=`echo $STAT | cut -f3 -d' '`

  # if user is owner
  if [ $USER_ID -eq $OWNER_UID ]; then
    echo -n $RIGHTS | head -c 1
  else
    for GID in `id -G $USER_ID`; do
        FOUND=0
        # if user in owner group
        if [ $GID -eq $OWNER_GID ]; then
          FOUND=1
          echo -n $RIGHTS | head -c 2 | tail -c 1
          break
        fi

        # if user not in owner group
        if [ $FOUND -ne 1 ]; then
          echo -n $RIGHTS | tail -c 1
        fi
    done
  fi
}


##
# MAIN BLOCK
##
FILE=$1

# if file is not defined
if [ -z ${FILE} ]; then
  exit 1;
fi

# if given file does not exist
if [ ! -e $FILE ]; then
  exit 2;
fi


ENTRY=`cat /etc/passwd | awk -F: '{ print $3 }'`
for USER in $ENTRY; do
  ENTRY=`get_rights_entry $FILE $USER`
  if [ $? -ne 0 ]; then
    echo "Could use ACL" >&2
  fi

  READ_PERMISSION=$(($ENTRY - 4))

  # if no read permission
  if [ $READ_PERMISSION -lt 0 ]; then
    ENTRY=`get_acl_rights_entry $FILE $USER`
    if [ $? -ne 0 ]; then
      echo "Error happened during getting simple rights: $?" >&2
      exit $?
    fi

    READ_PERMISSION=$(($ENTRY - 4))
  fi

  if [ $READ_PERMISSION -ge 0 ]; then
    cat /etc/passwd | awk -v user="$USER" -F: '{ if (strtonum($3) == user) { print $1 } }'
  fi
done
