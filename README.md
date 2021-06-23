# The Task Allocation System
This is the most complex project yet. It has been built as part of the Software Engineering Practice module. For this, students were required to work in teams of four and build a functional system for a company that wished to move the caretakers' lists of chores on a platform instead of having a paper-based system. I have rewritten the parts of my coleagues for copyright reasons, thus the code belongs enirely to me.  

**Difficulty level:** Advanced. 

**Programming language:** Java.

**How it works:** The original paper-based system of the company required the maintanace of two lists by the administrator: one for the routine cleaning chores and one for one-off chores, such as replacing a broken window, which are only completed once and then ignored. The issue with this paper-based system was the caretkares needed to read the lists each morning and assign the routine tasks to themselves. This was very time consuming, thus the main goal of the produced software was to randomly generate a list of routine tasks for each caretaker every day and sort them based on priority. The lists also allow for tradings if one caretkare reallly does not feel like completing a specific task that day. The one-off tasks are chosen by each caretaker as he completes the routine tasks for the day. These are deleted from the admnistrator list and added to their personal lists. No two caretakres can assign themselves the same one-off task. The only way to delete them from a personal list is either to complete it or bring it back into the administrator's list of one-off tasks using the trading option. Otherwise, the one-off tasks remain in their lists and as the day passes, only the routine tasks are replaced again by random assignment. It makes sense to delete the uncompleted routine tasks from each caretaker list as the same caretaker will not be required those tasks the next day too. This is why a reporting compnenet would be very useful to inform the adminsteatr which cleaning chores have been ignored so the responsible caretaker can be santioned.   

**How it was built:** The GUIs of the program were built using the Eclipse Windowbuilder plugin. The program's archtecture follws the model-view-controller paradigm. Thus, the GUIs only deal with displaying while the controller carries out the actions required by the interfaces. After that, the task allocation database class takes care of the rest. All lists are generated dynamically from the database such that for each caretaker registered in the caretakers table will have a list. The display table data methods of the two interfaces were particularly challenging as they required to pass a reference to the controller and vice-versa in order to update tables whenever an operation on a list was carried out. For this, hash maps were implemeted. The hash maps in the view classes map the name of a caretaker to their generated table, while the same caretaker names in the controller class map to their corresponding lists. This way, the controller simply passes the data from the list to the interface which reloads the table.  

> IMPORTANT: The code still requires some modifications, as it does not save the generated caretaker lists to the database, but is rather a prototype of the actual system. However, the administrator functionality is completely tied up to the database. Furthermore, the login functionality has not been implemented yet. It would also be ideal to include a reporting functionality as mentioned. 

## Administrator functionality 
https://user-images.githubusercontent.com/60512873/122923091-25fe5480-d319-11eb-9ebf-90a0e53890a1.mp4

## Caretakers functionality
https://user-images.githubusercontent.com/60512873/123063907-aa58e200-d3c2-11eb-9fae-fc40abfd6c07.mp4

https://user-images.githubusercontent.com/60512873/123063935-b0e75980-d3c2-11eb-9f92-82ab8cdd7993.mp4

https://user-images.githubusercontent.com/60512873/123063988-bd6bb200-d3c2-11eb-83a3-036895838b37.mp4

https://user-images.githubusercontent.com/60512873/123064040-c8bedd80-d3c2-11eb-96a1-cafadfc5b613.mp4













