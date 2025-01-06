package com.example.fs.knowledge.service;

import com.example.fs.knowledge.pojo.AddKnowledgeTextDTO;
import com.example.fs.knowledge.pojo.DeleteKnowledgeTextDTO;
import com.example.fs.knowledge.pojo.UpdateKnowledgeTextDTO;

/**
 * @author Adss
 * @data 2025/1/6 14:13
 */
public interface KnowledgeTextService {
    void add(AddKnowledgeTextDTO dto);

    void delete(DeleteKnowledgeTextDTO dto);

    void update(UpdateKnowledgeTextDTO dto);
}
