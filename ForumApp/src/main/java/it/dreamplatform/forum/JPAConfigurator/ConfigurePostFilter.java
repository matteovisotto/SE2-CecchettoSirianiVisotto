package it.dreamplatform.forum.JPAConfigurator;

import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;

import org.eclipse.persistence.expressions.Expression;
import org.eclipse.persistence.expressions.ExpressionBuilder;
import org.eclipse.persistence.mappings.OneToManyMapping;


public class ConfigurePostFilter implements DescriptorCustomizer {
    public void customize(ClassDescriptor descriptor) throws Exception {
        OneToManyMapping mapping = (OneToManyMapping) descriptor
                .getMappingForAttributeName("posts");

        ExpressionBuilder eb = new ExpressionBuilder(mapping
                .getReferenceClass());
        Expression activeExp = eb.getField("status").equal(1);
        mapping.setSelectionCriteria(activeExp);
    }
}
