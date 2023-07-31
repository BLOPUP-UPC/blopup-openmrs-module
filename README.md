This is a custom OpenMRS module to upload the recordings of the patients' legal consent and store it in our server. 

To add a new version of the medule to OpenMRS, you will need to first release the module OMOD file.

1. Increase the version number for the OMOD file in .......
2. Add a tag to the commit you want to publish. 
  Tag name: should be the name of the module + the release version
  Tag message: One sentence summary of the release content
   `git tag -a <tag_name> -m "<tag_description>"`
3. Push the tag alongisde the latest commit
   `git push --tags`
   
You should see a new Release version appear with the name of the tag you pushed.

