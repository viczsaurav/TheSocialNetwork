# The Social Network Graph


- We use SocialGraph class to keep track of all nodes(GraphNode) in the network.

- We use GraphNode to keep track of the Node Value(Person) + neighbours of the node
- We use Person entity to store the the email. 

Note: 

- The first version of the solution assumes email id to provide 1-to-1 mapping with a person.

- We did not consider Name as part of this class as there is a lot of noise in the Tand X-To fields of .eml file and many times the count in the above list does not match.


Class Details:
 - `main.ApplicationMain` : Entrypoint of the solution and setup the environment
 - `model.SocialGraph` : keep track of all nodes(GraphNode) in the network
 - `model.GraphNode` : keep track of the Node Value(Person) + neighbours of the node.
 - `model.Person` : Person entity to store the the email (1:1 mapping)
 - `utils.EMLParser` : Parser to read .eml files and extract sender and recipients
 - `utils.Utilities` : Various utility functions used in program.
 
Resources:
 - `config.properties` : Environment variables for input and out of program.