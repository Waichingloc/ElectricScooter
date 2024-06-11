export class RESTAdaptor {
    constructor(resourceUrl, copyConstructor) {
        this.resourcesUrl = resourceUrl; // The full URL of the backend resource endpoint
        this.copyConstructor = copyConstructor; // A reference to the copyConstructor of the entity: (data) => data
    }

    /**
     * Retrieves all entities that match the optional queryParams.
     * @param {Object} queryParams Optional query parameters
     * @returns {Promise<Array>} Promise resolving to an array of entities
     */
    async findAll(queryParams = null) {
        // Implementation goes here
        return [];
    }

    /**
     * Retrieves the entity with the given id and applies optional queryParams.
     * @param {*} id The id of the entity to retrieve
     * @param {Object} queryParams Optional query parameters
     * @returns {Promise<Object>} Promise resolving to the retrieved entity
     */
    async findById(id, queryParams = null) {
        // Implementation goes here
        return {};
    }

    /**
     * Saves the given entity and applies optional queryParams.
     * @param {Object} entity The entity to save
     * @param {Object} queryParams Optional query parameters
     * @returns {Promise<Object>} Promise resolving to the saved entity
     */
    async save(entity, queryParams = null) {
        // Implementation goes here
        return entity;
    }

    /**
     * Deletes the entity with the given id and applies optional queryParams.
     * @param {*} id The id of the entity to delete
     * @param {Object} queryParams Optional query parameters
     * @returns {Promise<void>} Promise resolving to void
     */
    async delete(id, queryParams = null) {
        // Implementation goes here
    }
}

// TODO: Provide a generic implementation of this RESTAdaptor interface.
// That implementation does not know the generic type of the entity that it is servicing.
// So the constructor of the service shall accept a reference to the copyConstructor static method
// that can be used to convert received data objects to true instances:
// constructor(resourceUrl, copyConstructor)
