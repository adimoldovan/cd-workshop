import mysql.connector

DB = mysql.connector.connect(
    host="localhost:33069",
    # host='localhost',
    user='callicoder',
    passwd='callicoder',
    database='polls'
)


def get_records(query, **q_parameters):
    cursor = DB.cursor()
    if 'params' in q_parameters:
        cursor.execute(query, q_parameters['params'])
    else:
        cursor.execute(query)
    return cursor.fetchall()


def main():
    print('Test')
    rows = get_records("Select count(*) from users");
    for r in rows:
        print(r[0])

if __name__ == "__main__":
    main()
