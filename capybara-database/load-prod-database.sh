# Clear database: https://stackoverflow.com/a/13823560

# Connect and run file
psql "host=capybara-postgres-dev-database.postgres.database.azure.com port=5432 dbname=capybara_db user={user} password={pass} sslmode=require" -f ./capybara-database/sql-scripts/container-backup.sql -a