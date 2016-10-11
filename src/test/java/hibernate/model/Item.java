package hibernate.model;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "ITEM")
public class Item implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    protected Long id;

    @Column
    protected String code;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    protected Set<Component> components = new LinkedHashSet<>();

    @Override
    public int hashCode()
    {
        return Objects.hash(code);
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
        {
            return true;
        }

        if(obj == null || !(obj instanceof Item))
        {
            return false;
        }

        Item other = (Item) obj;

        return Objects.equals(code, other.getCode());
    }

    @Override
    public String toString()
    {
        return getClass().getSimpleName() + ": id=[" + id + "] code=[" + code + "]";
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public Set<Component> getComponents()
    {
        return components;
    }

    public void setComponents(Set<Component> components)
    {
        this.components = components;
    }
}
