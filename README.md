# Readme

## Description

This is a custom OpenMRS module to add new functionality directly related to the Blopup Mobile App.
This is a breakdown of the repository content:

- File upload controller and service to upload legal consent audio files
- Controller and service to send a message to a specific doctor via Telegram
- Liquibase migrations to support treatment type encounters and observations
- Liquibase migrations to support sending messages to the doctor (adding chatID as provider attribute)

## Releasing a new version

To add a new version of the medule to OpenMRS, you will need to first release the module OMOD file.

**1. Increase the version number for the project in all `pom.xml` files.**

- root pom.xml project version

- omod/pom.xml parent version

- api/pom.xml parent version
   
**2. Add a tag to the commit you want to publish.**
  
*Tag name: should be the name of the module + the release version*

*Tag message: One sentence summary of the release content*

`git tag -a <tag_name> -m "<tag_description>"`
   
**3. Push the tag alongisde the latest commit**
   `git push --tags`
   
You should see a new Release version appear with the name of the tag you pushed.

