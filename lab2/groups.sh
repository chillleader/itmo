# Print groups that consist of more than N users
# Sorted alphabetically

USERS_AMOUNT=$1

if [ -z ${USERS_AMOUNT} ];
then
  echo "Error: Amount of users is not defined" >&2
  exit 1
fi

if [ ! -x /etc/group -o ! -r /etc/group ];
then
  echo "Error: No permission to access /etc/group" >&2
  exit 2
fi

awk -v USERS_AMOUNT=$USERS_AMOUNT -F':' '{ n = split($0,users,","); if(n >= USERS_AMOUNT) { print $1 }  }' /etc/group | sort
