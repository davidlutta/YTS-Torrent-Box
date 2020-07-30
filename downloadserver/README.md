# Download Server
This is the download server which should be running on your raspberry pi or if you like on your machine.
# Prerequisites
- Firebase Project with firestore enabled
# Setup
1. Install `transmission daemon`. Transmission daemon **MUST** be installed on your raspberry pi or your machine for this entire project to work. Learn how to install using this [link](https://help.ubuntu.com/community/TransmissionHowTo).
2. Add `serviceAccount.json` to the root of the directory as this server communicates directly to your firestore database.
3. Create a `.env` file and add `TUSERNAME` which is your transmission username and `TPASSWORD` which is your transmission password and finally `DB_URL` which is the firestore url.
4. After installing transmission daemon run `npm intstall` to install all the necessary packages.
5. Run `npm start` to start the server. You can install a process manager such as [pm2](https://www.npmjs.com/package/pm2) to keep the server running forever in the background.

# Libraries Used
- firebase-admin
- Transmission
- Schedule
- Dotenv
