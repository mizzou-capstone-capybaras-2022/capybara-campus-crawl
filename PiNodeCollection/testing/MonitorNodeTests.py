from ... import MonitorNodes


def test_database_connection():
    dbPass = None
    dbHost = None

    with open('/etc/pi_env.json') as json_file:
        data = json.load(json_file)
        dbPass = data['PiNodeDBPass']
        dbHost = data['PiNodeDBHost']

    #This should be consistant
    dbName = "capybara_db"
    dbPort = 5432#os.environ.get('PiNodeDBPort')

    db = initialize_database(dbName,dbPass,dbHost,dbPort)

    assert db is not None
    